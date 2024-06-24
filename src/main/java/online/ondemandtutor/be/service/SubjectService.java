package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.enums.RequestStatus;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.DayAndSlotRequest;
import online.ondemandtutor.be.model.EmailDetail;
import online.ondemandtutor.be.model.SubjectRegisterRequest;
import online.ondemandtutor.be.model.UpRoleRequestByAccountId;
import online.ondemandtutor.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private EducationLevelRepository educationLevelRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WeekDayRepository weekDayRepository;

    @Autowired
    private TeachingSlotRepository teachingSlotRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private ScheduleRecordRepository scheduleRecordRepository;
    @Autowired
    private EmailService emailService;


    //CRUD: read all
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    //CRUD: read by name
    public List<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    //CRUD: create
//    public Subject createSubject(SubjectRequest subjectRequest) {
//        Subject subject = new Subject();
//
//        //tìm ID nhận vào từ SubjectRequest
//        Category category = categoryRepository.findById(subjectRequest.getCategoryId());
//        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(subjectRequest.getScheduleId());
//
//        subject.setName(subjectRequest.getName());
////        subject.setGrade(subjectRequest.getGrade());
//        subject.setCategory(category);
//
//        subject.setTutorSchedules(schedule);
//
//        Subject newSubject = subjectRepository.save(subject);
//        return newSubject;
//    }
//
//    //CRUD: update
//    public Subject updateSubject(SubjectRequest subjectRequest, long id) {
//        Subject subject = subjectRepository.findById(id);
//        Category category = categoryRepository.findById(subjectRequest.getCategoryId());
//        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(subjectRequest.getScheduleId());
//
//        subject.setName(subjectRequest.getName());
////        subject.setGrade(subjectRequest.getGrade());
//        subject.setCategory(category);
//
//        subject.setTutorSchedules(schedule);
//
//        Subject newSubject = subjectRepository.save(subject);
//        return newSubject;
//    }

    //CRUD: delete
//    public Subject deleteSubject(long id) {
//        Subject subject = subjectRepository.findById(id);
//        if (subject != null) {
//            subject.setDeleted(true);
//            Subject newSubject = subjectRepository.save(subject);
//            return newSubject;
//        }
//        else {
//            return null;
//        }
//    }



    public void SubjectRegister(SubjectRegisterRequest request) {
        EducationLevel eduLv = educationLevelRepository.findEducationLevelById(request.getEducationLevelId());
        Account account = authenticationRepository.findAccountById(request.getAccountId());
        account.setEducationLevel(eduLv);
        authenticationRepository.save(account);

//        List<Location> locations = locationRepository.findAllById(request.getLocationIds());
//        account.setLocations(locations);


        ArrayList<Location> locationList = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        for(Long findId : request.getLocationIds()){
            Location location = locationRepository.findLocationById(findId);
            locationList.add(location);
            accounts = location.getAccount();
            accounts.add(account);
            location.setAccount(accounts);
        }
        account.setLocations(locationList);
        authenticationRepository.save(account);

        ArrayList<Grade> gradeList = new ArrayList<>();
//        List<Account> accounts = new ArrayList<>();
        for(Long findId : request.getGradeIds()){
            Grade grade = gradeRepository.findGradeById(findId);
            gradeList.add(grade);
            accounts = grade.getAccount();
            accounts.add(account);
            grade.setAccount(accounts);
        }
        account.setGrades(gradeList);
        authenticationRepository.save(account);

        ArrayList<Subject> subjectList = new ArrayList<>();
//        List<Account> accounts = new ArrayList<>();
        for(Long findId : request.getGradeIds()){
            Subject subject = subjectRepository.findSubjectById(findId);
            subjectList.add(subject);
            accounts = subject.getAccount();
            accounts.add(account);
            subject.setAccount(accounts);
        }
        account.setGrades(gradeList);
        authenticationRepository.save(account);


        TutorVideo tutorVideo = new TutorVideo();
        tutorVideo.setUrl(request.getTutorVideoUrl());

        for (DayAndSlotRequest dayAndSlot: request.getDayAndSlotRequests()){
            for (Long slotId : dayAndSlot.getTeachingSlotIds()){
                ScheduleRecord scheduleRecord =  scheduleRecordRepository.findByWeekDayIdAndTeachingSlotId(dayAndSlot.getWeekDayIds(),slotId);
                List<Account> lists = new ArrayList<>();
                if(scheduleRecord == null){
                    scheduleRecord = new ScheduleRecord();
                    WeekDay weekDay =  weekDayRepository.findWeekDayById(dayAndSlot.getWeekDayIds());
                    TeachingSlot teachingSlot = teachingSlotRepository.findTeachingSlotById(slotId);
                    scheduleRecord.setWeekDay(weekDay);
                    scheduleRecord.setTeachingSlot(teachingSlot);
                    lists.add(account);
                    scheduleRecord.setAccount(lists);
                    scheduleRecordRepository.save(scheduleRecord);
                }else{
                    lists = scheduleRecord.getAccount();
                    scheduleRecord.setAccount(lists);
                    scheduleRecordRepository.save(scheduleRecord);
                }
            }

        }
        account.setSubjectRegistrationStatus(RequestStatus.PENDING);
        SendUpRoleRegistrationToModerator(account);
    }

    public void SendUpRoleRegistrationToModerator(Account student){
        List<Account> listMod = authenticationRepository.findAccountByRole(RoleEnum.MODERATOR);
        for (Account mod : listMod) {
            //copy tu ForgetPassword
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(mod.getEmail());
            emailDetail.setSubject("Subject Registration Request for account " + student.getEmail() + "!");
            emailDetail.setMsgBody("");
            emailDetail.setButtonValue("Lets start to study!");
            emailDetail.setFullName(mod.getFullname());
            // chờ FE gửi link dashboard up role
            emailDetail.setLink("http://localhost:5173/account");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    emailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        }
    }

    public Account ApprovedSubject(UpRoleRequestByAccountId id){

        Account account = authenticationRepository.findAccountById(id.getAccountId());

        if(account != null){
            account.setSubjectRegistrationStatus(RequestStatus.APPROVED);
            SendEmailToTutor(account, "APPROVED!");
            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    //chuc nang duoi la` cho MOD bam duyet rejected trong DASHBOARD
    public Account RejectedSubject(UpRoleRequestByAccountId id){

        Account account = authenticationRepository.findAccountById(id.getAccountId());

        if(account != null){
            account.setSubjectRegistrationStatus(RequestStatus.REJECTED);
            SendEmailToTutor(account, "REJECTED!");
            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    public void SendEmailToTutor(Account account, String msg){

        //copy tu ForgetPassword
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setSubject("Response from MODERATOR for " + account.getEmail() + " subject registration request!");
        emailDetail.setMsgBody(msg);
        // chờ FE gửi web chính thức
        emailDetail.setButtonValue("Welcome to new subject!");
        emailDetail.setFullName(account.getFullname());
        // chờ FE gửi link trang web
        emailDetail.setLink("http://ondemandtutor.online/login");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();

    }

    public List<Account> getAllAccountsHaveSubjectRegistrationRequest(){
        return authenticationRepository.findAccountsBySubjectRegistrationStatus(RequestStatus.PENDING);
    }

    public List<Account> getAllAccountsHaveApprovedSubjectRegistrationRequest(){
        return authenticationRepository.findAccountsBySubjectRegistrationStatus(RequestStatus.APPROVED);
    }

}
