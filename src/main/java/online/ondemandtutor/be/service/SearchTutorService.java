package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.SearchTutor;
import online.ondemandtutor.be.model.SearchTutorRequest;
import online.ondemandtutor.be.repository.SearchTutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTutorService {

    @Autowired
    SearchTutorRepository searchTutorRepository;

    public List<SearchTutor> searchTutorsByName(SearchTutorRequest searchTutorRequest) {
        return searchTutorRepository.findByNameContainingIgnoreCase(searchTutorRequest.getName());
    }
}
