package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.webService.SampleSubmodelFactory;
import com.sap.i40aas.datamanager.webService.Topic;
import identifiables.Submodel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class SubmodelsObjectsService {

    private SubmodelsObjectsService aasObjectService;

    private final List<Topic> topics = new ArrayList<Topic>(Arrays.asList(
            new Topic("spring", "Srping Framework", "Sample Description"),
            new Topic("spring1", "Srping Framework", "Sample Description"),
            new Topic("spring2", "Srping Framework", "Sample Description")
    ));


    private final List<Submodel> submodels = new ArrayList<>(Arrays.asList(
            SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
            SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2")
    ));


    public List<Topic> getAllTopics() {
        return topics;
    }

    public Submodel getSubmodel(String idShort) {

        return submodels.stream().filter(submodel -> submodel.getIdShort().equals(idShort)).findFirst().get();
    }

    public Topic getTopic(String id) {
        return topics.stream().filter(topic -> topic.getId().equals(id)).findFirst().get();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void updateTopic(String id, Topic topic) {
        topics.set(topics.indexOf(topics.stream().filter(topic1 -> topic1.getId().equals(id)).findFirst().get()), topic);
    }

    public void deleteTopic(String id) {

        topics.removeIf(topic -> topic.getId().equals(id));

    }

    public List<Submodel> getAllSubmodels() {
        return submodels;
    }

    public void updateSubmodel(String idShort, Submodel submodel) {
        if (submodels.stream().anyMatch(submodel1 -> submodel.getIdShort().equals(idShort))) {
            submodels.set(submodels.indexOf(submodels.stream().filter(submodel1 -> submodel.getIdShort().equals(idShort)).findFirst().get()), submodel);
        } else
            submodels.add(submodel);
    }

    public void deleteSubmodel(String idShort) {
        submodels.removeIf(t -> t.getIdShort().equals(idShort));
    }
}
