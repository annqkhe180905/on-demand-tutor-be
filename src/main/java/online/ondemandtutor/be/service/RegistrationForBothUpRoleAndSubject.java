//package online.ondemandtutor.be.service;
//
//import online.ondemandtutor.be.entity.*;
//import online.ondemandtutor.be.model.RegistrationForBothUpRoleAndSubjectRequest;
//import online.ondemandtutor.be.repository.EducationLevelRepository;
//import online.ondemandtutor.be.repository.LocationRepository;
//import online.ondemandtutor.be.repository.RegisterRepository;
//import online.ondemandtutor.be.repository.SubjectRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class RegistrationForBothUpRoleAndSubject {
//    @Autowired
//    AuthenticationService authenticationService;
//    @Autowired
//    SubjectRepository subjectRepository;
//    @Autowired
//    EducationLevelRepository educationLevelRepository;
//    @Autowired
//    LocationRepository locationRepository;
//    @Autowired
//    RegisterRepository registerRepository;
//
//
//    // thuoc tinh ben model
////    Long educationLevelId;
////    List<Long> locationIds;
////    List<Long> subjectIds;
////    String TutorVideoUrl;
////    String brief;
//
//
//
//    public void registrationForm (RegistrationForBothUpRoleAndSubjectRequest object){
//
//        Register register =
//        Account account = authenticationService.getCurrentAccount();
//
//        //lay thong tin tu request de tra cuu trong db
//        EducationLevel level = educationLevelRepository.findById(object.getEducationLevelId()).get();
//        ArrayList<Location> locations = new ArrayList<>();
//
//        for(Long findId: object.getLocationIds()){
//            Location location = locationRepository.findById(findId).get();
//            locations.add(location);
//        }
//
//        ArrayList<Subject> subjects = new ArrayList<>();
//        for(Long findId: object.getSubjectIds()){
//            Subject subject = subjectRepository.findById(findId).get();
//            subjects.add(subject);
//        }
//
//        //thieu video url
//
//        String brief = object.getBrief();
//
//        return registerRepository.save();
//
//
//    }
//}
