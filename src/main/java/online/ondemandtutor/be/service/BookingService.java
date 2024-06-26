package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.enums.StatusEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.BookingRequest;
import online.ondemandtutor.be.model.EmailDetail;
import online.ondemandtutor.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

        booking.setStatus(StatusEnum.PENDING);

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

        sendBookingConfirmationToTutor(tutor, student);
        return bookingRepository.save(booking);
    }

//    // lấy ds tất cả booking
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }
//
//    // tìm 1 booking theo id
//    public Booking getBookingById(Long id) {
//        return bookingRepository.findBookingById(id);
//    }

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


//    // gui booking mới đến mod duyệt
//    public void sendBookingRequestToModerator(Booking booking, Account student) {
//        List<Account> listMod = authenticationRepository.findAccountByRole(RoleEnum.MODERATOR);
//        for (Account mod : listMod) {
//            EmailDetail emailDetail = new EmailDetail();
//            emailDetail.setRecipient(mod.getEmail());
//            emailDetail.setSubject("New Booking Request for account " + student.getEmail() + "!");
//            emailDetail.setMsgBody("A new booking request has been made by " + student.getFullname() + ". Please review the request.");
//            emailDetail.setButtonValue("Review Booking");
//            emailDetail.setFullName(mod.getFullname());
//            emailDetail.setLink("http://localhost:5173/booking-review?bookingId=" + booking.getId());
//
//            Runnable r = new Runnable() {
//                @Override
//                public void run() {
//                    EmailService.sendMailTemplate(emailDetail);
//                }
//            };
//            new Thread(r).start();
//        }
//    }

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
