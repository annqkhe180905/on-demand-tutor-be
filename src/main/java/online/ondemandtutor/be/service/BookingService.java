package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;

import online.ondemandtutor.be.enums.BookingEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.BookingDetailResponse;
import online.ondemandtutor.be.model.request.*;
import online.ondemandtutor.be.model.EmailDetail;
import online.ondemandtutor.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    EmailService emailService;

    // tạo mới booking dựa trên booking request
    public Booking createBooking(BookingRequest bookingRequest) {
        Account student = authenticationService.getCurrentAccount();
        Account tutor = authenticationRepository.findAccountById(bookingRequest.getTutorId());

        if (student == null) {
            throw new RuntimeException("Student not found with id " + student.getId());
        }

        if (tutor == null) {
            throw new RuntimeException("Tutor not found with id " + bookingRequest.getTutorId());
        }
        if (bookingRepository.findBookingByStudentIdAndTutorIdAndStatus
                (student.getId(), tutor.getId(), BookingEnum.PENDING) != null) {
            throw new BadRequestException("You have already sent a booking request to this tutor!");
        }
        if (bookingRepository.findBookingByStudentIdAndTutorIdAndStatus
                (student.getId(), tutor.getId(), BookingEnum.APPROVED) != null) {
            throw new BadRequestException("You must finish the previous course to create the same booking request to this tutor!");
        }

        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setTutor(tutor);
        booking.setStatus(BookingEnum.PENDING);

        sendBookingRequestToTutor(tutor, student);
        return bookingRepository.save(booking);
    }


    // gửi email thông báo booking mới cho tutor
    public void sendBookingRequestToTutor(Account tutor, Account student) {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(tutor.getEmail());
        emailDetail.setSubject("New Booking Request from " + student.getEmail());
        emailDetail.setMsgBody("You have a new booking request from " + student.getFullname() + ".");
        emailDetail.setButtonValue("View Booking Details");
        emailDetail.setFullName(tutor.getFullname());
        emailDetail.setLink("http://ondemandtutor.online");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    // chấp nhận booking của một student
    public Booking approveBookingRequest(BookingRequestById bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId.getBookingId());
        booking.setStatus(BookingEnum.APPROVED);
        sendBookingStatusEmailToStudent(booking, "APPROVED!");
        return bookingRepository.save(booking);
    }

    // từ chối booking của một student
//    public Booking approveBookingRequest(BookingRequestById bookingId) {
//        Booking booking = bookingRepository.findBookingById(bookingId.getBookingId());
//        booking.setStatus(BookingEnum.REJECTED);
//        sendBookingStatusEmailToStudent(booking, "REJECTED!");
//        return bookingRepository.save(booking);
//    }
    public Booking rejectBookingRequest(BookingRequestById bookingId) {
//        Account student = authenticationService.getCurrentAccount();
        Booking booking = bookingRepository.findBookingById(bookingId.getBookingId());
//        Account tutor = booking.getTutor();
        booking.setStatus(BookingEnum.REJECTED);
//        booking.setStudent(student);
//        booking.setTutor(tutor);
        sendBookingStatusEmailToStudent(booking, "REJECTED!");
        return bookingRepository.save(booking);
    }

    public Booking passedCourse(BookingRequestById bookingId){
        Booking booking = bookingRepository.findBookingById(bookingId.getBookingId());
        booking.setStatus(BookingEnum.PASSED);
        sendBookingStatusEmailToStudent(booking, "PASSED!");
        return bookingRepository.save(booking);
    }

    public Booking failedCourse(BookingRequestById bookingId){
        Booking booking = bookingRepository.findBookingById(bookingId.getBookingId());
        booking.setStatus(BookingEnum.FAILED);
        sendBookingStatusEmailToStudent(booking, "FAILED!");
        return bookingRepository.save(booking);
    }

    private void sendBookingStatusEmailToStudent(Booking booking, String status) {
        Account student = booking.getStudent();
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(student.getEmail());
        emailDetail.setSubject("Your booking request has been " + status);
        emailDetail.setMsgBody("Your booking request has been " + status.toLowerCase() + ".");
        emailDetail.setButtonValue("View Booking Details");
        emailDetail.setFullName(student.getFullname());
        emailDetail.setLink("http://ondemandtutor.online");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    public BookingDetailResponse getBookingById(Long id) {
        Booking studentBookingDetail = bookingRepository.findBookingById(id);
        if(studentBookingDetail != null) {
            Account student = studentBookingDetail.getStudent();
            Account tutor = studentBookingDetail.getTutor();

            BookingDetailResponse response = new BookingDetailResponse();
            response.setBooking(studentBookingDetail);
            response.setStudent(student);
            response.setTutor(tutor);
            return response;
        } else {
            throw new BadRequestException("Your booking has not found!");
        }
    }

    private BookingDetailResponse convertToBookingDetailResponse(Booking booking) {
        Account student = booking.getStudent();
        Account tutor = booking.getTutor();
        BookingDetailResponse response = new BookingDetailResponse();
        response.setBooking(booking);
        response.setStudent(student);
        response.setTutor(tutor);
        return response;
    }

    // Modified studentListOfBookingsById method
    public List<BookingDetailResponse> studentListOfBookingsById(Long id) {
        List<Booking> studentBooking = bookingRepository.findBookingByStudentId(id);
        if (studentBooking != null) {
            return studentBooking.stream()
                    .map(this::convertToBookingDetailResponse)
                    .collect(Collectors.toList());
        } else {
            throw new BadRequestException("Your booking list has not found!");
        }
    }

    // Modified tutorListOfBookingsById method
    public List<BookingDetailResponse> tutorListOfBookingsById(Long tutorId) {
        List<Booking> tutorBooking = bookingRepository.findBookingByTutorId(tutorId);
        if (tutorBooking != null) {
            return tutorBooking.stream()
                    .map(this::convertToBookingDetailResponse)
                    .collect(Collectors.toList());
        } else {
            throw new BadRequestException("Tutor booking list has not found!");
        }
    }

}
