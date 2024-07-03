package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectRegisterService {

    @Autowired
    private EducationLevelRepository educationLevelRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private WeekDayRepository weekDayRepository;
    @Autowired
    private TeachingSlotRepository teachingSlotRepository;


    public List<EducationLevel> listOfEducationLevels(){
        return educationLevelRepository.findAll();
    }

    public List<Location> listOfLocations(){
        return locationRepository.findAll();
    }

    public List<Grade> listOfGrades(){
        return gradeRepository.findAll();
    }

    public List<WeekDay> listOfWeekDays(){
        return weekDayRepository.findAll();
    }

    public List<TeachingSlot> listOfTeachingSlots(){
        return teachingSlotRepository.findAll();
    }

}
