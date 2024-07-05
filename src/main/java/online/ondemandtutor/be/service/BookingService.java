package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.enums.RequestStatus;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.BookingRequest;
import online.ondemandtutor.be.model.DayAndSlotRequest;
import online.ondemandtutor.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
//    @Autowired
//    private EducationLevelRepository educationLevelRepository;
//    @Autowired
//    private LocationRepository locationRepository;
//    @Autowired
//    private GradeRepository gradeRepository;



    // tạo mới booking dựa trên booking request
    public Booking createBooking(BookingRequest bookingRequest) {
        Account student = authenticationService.getCurrentAccount();
        Account tutor = authenticationRepository.findAccountById(bookingRequest.getTutorId());

        Booking booking = new Booking();

        ArrayList<Booking> studentList = new ArrayList<>();
        if(student != null){
            booking.getStudents().add(student);
            studentList.add(booking);
        }else{
            throw new RuntimeException("Student not found with id " + student.getId());
        }

        ArrayList<Booking> tutorList = new ArrayList<>();
        if(tutor != null){
            booking.getTutors().add(tutor);
            tutorList.add(booking);
        }else{
            throw new RuntimeException("Tutor not found with id " + bookingRequest.getTutorId());
        }




        //information
//        EducationLevel eduLv = educationLevelRepository.findEducationLevelById(bookingRequest.getLiteracy());
//
//        ArrayList<Grade> gradeList = new ArrayList<>();
////        List<Account> accounts = new ArrayList<>();
//        for(Long findId : bookingRequest.getClassTaught()){
//            Grade grade = gradeRepository.findGradeById(findId);
//            gradeList.add(grade);
//            accounts = grade.getAccount();
//            accounts.add(account);
//            grade.setAccount(accounts);
//        }
//        account.setGrades(gradeList);
//        authenticationRepository.save(account);
//
//        ArrayList<Subject> subjectList = new ArrayList<>();
////        List<Account> accounts = new ArrayList<>();
//        for(Long findId : bookingRequest.getSubjectTaught()){
//            Subject subject = subjectRepository.findSubjectById(findId);
//            subjectList.add(subject);
//            accounts = subject.getAccount();
//            accounts.add(account);
//            subject.setAccount(accounts);
//        }
//        account.setGrades(gradeList);
//        authenticationRepository.save(account);
//
//
//        TutorVideo tutorVideo = new TutorVideo();
//        tutorVideo.setUrl(request.getTutorVideoUrl());
//        tutorVideo.setAccount(account);
////        account.getTutorVideos().add(tutorVideo);
//        tutorVideoRepository.save(tutorVideo);
//
//        account.setBrief(request.getBrief());
//        authenticationRepository.save(account);

        if(booking.getStatus() == StatusEnum.PENDING){
            throw new BadRequestException("Your booking is already in pending status!");
        }
        booking.setStatus(StatusEnum.PENDING);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(BookingRequest bookingRequest) {
        Account student = authenticationService.getCurrentAccount();
        Account tutor = authenticationRepository.findAccountById(bookingRequest.getTutorId());

        Booking booking = new Booking();

        ArrayList<Booking> studentList = new ArrayList<>();
        if(student != null){
            booking.getStudents().add(student);
            studentList.add(booking);
        }else{
            throw new RuntimeException("Student not found with id " + student.getId());
        }

        ArrayList<Booking> tutorList = new ArrayList<>();
        if(tutor != null){
            booking.getTutors().add(tutor);
            tutorList.add(booking);
        }else{
            throw new RuntimeException("Tutor not found with id " + bookingRequest.getTutorId());
        }

        if(booking.getStatus() == StatusEnum.PENDING){
            throw new BadRequestException("Your booking is already in pending status!");
        }
      
        sendBookingConfirmationToTutor(tutor, student);
        booking.setStatus(StatusEnum.PENDING);
        return bookingRepository.save(booking);
    }

    


    // update booking dựa trên booking request và id
//    public Booking updateBooking(Long id, BookingRequest bookingRequest) {
//        Booking optionalBooking = bookingRepository.findBookingById(id);
//        if (optionalBooking != null) {
//            Booking booking = optionalBooking;
//            booking.setLiteracy(bookingRequest.getLiteracy());
//            booking.setDesiredTutoringLocation(bookingRequest.getDesiredTutoringLocation());
//            booking.setTutoringClass(bookingRequest.getClassTaught());
//            booking.setSubjectTaught(bookingRequest.getSubjectTaught());
//            booking.setBrief(bookingRequest.getBrief());
//            // Đảm bảo trạng thái của booking vẫn là OK
//            booking.setStatus("OK");
//            return bookingRepository.save(booking);
//        } else {
//            throw new RuntimeException("Booking not found with id " + id);
//        }
//    }

    // xóa booking
//    public void deleteBooking(Long id) {
//        if (bookingRepository.existsById(id)) {
//            bookingRepository.deleteById(id);
//        } else {
//            throw new RuntimeException("Booking not found with id " + id);
//        }
//    }

    // get all booking
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // find booking by id
    public Booking getBookingById(Long id) {
        return bookingRepository.findBookingById(id);
    }


    // gửi email thông báo booking mới cho tutor
    public void sendBookingConfirmationToTutor(Account tutor, Account student) {
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
                EmailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    // chấp nhận booking của một student
    public Booking approveBookingRequest(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking != null) {
            booking.setStatus(StatusEnum.APPROVED);
            sendBookingApprovalEmail(booking);
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Booking not found!");
        }
    }

    // từ chối booking của một student
    public Booking rejectBookingRequest(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking != null) {
            booking.setStatus(StatusEnum.REJECTED);
            sendBookingRejectionEmail(booking);
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Booking not found!");
        }
    }

    // gửi email xác nhận chấp nhận booking
    private void sendBookingApprovalEmail(Booking booking) {
        for (Account student : booking.getStudents()) {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(student.getEmail());
            emailDetail.setSubject("Your booking request has been approved!");
            emailDetail.setMsgBody("Congratulations! Your booking request has been approved.");
            emailDetail.setButtonValue("View Booking Details");
            emailDetail.setFullName(student.getFullname());
            emailDetail.setLink("http://ondemandtutor.online");

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    EmailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        }
    }

    // gửi email thông báo từ chối booking
    private void sendBookingRejectionEmail(Booking booking) {
        for (Account student : booking.getStudents()) {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(student.getEmail());
            emailDetail.setSubject("Your booking request has been rejected");
            emailDetail.setMsgBody("We regret to inform you that your booking request has been rejected.");
            emailDetail.setButtonValue("View Details");
            emailDetail.setFullName(student.getFullname());
            emailDetail.setLink("http://ondemandtutor.online");

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    EmailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        }
    }

}
