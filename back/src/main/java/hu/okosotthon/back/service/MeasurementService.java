package hu.okosotthon.back.service;

import hu.okosotthon.back.controller.AuthController;
import hu.okosotthon.back.dto.MeasurementDTO;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.model.Topic;
import hu.okosotthon.back.repository.DeviceRepo;
import hu.okosotthon.back.repository.FieldsRepo;
import hu.okosotthon.back.repository.MeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class MeasurementService {

    private MeasurementRepo measurementRepo;
    private FieldsRepo fieldsRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo, FieldsRepo fieldsRepo) {
        this.measurementRepo = measurementRepo;
        this.fieldsRepo = fieldsRepo;
    }


    public void save(Measurement measurement) {
        this.measurementRepo.save(measurement);
    }

    public List<Measurement> findMeasurementByUserId() {
        return this.measurementRepo.findMeasurementByUserId(AuthController.currentUser.getUserId());
    }

    public List<Measurement> getAllMeasurementByDeviceId(int deviceId) {
        return this.measurementRepo.getAllMeasurementByDeviceId(deviceId);
    }

    //TODO itt majd azt meg kell oldani hogy a felhasználó mennyi értéket akart kérni
    public List<MeasurementDTO> getDeviceMeasurement(int devicesId) {
        Pageable p;

        List<Fields> deviceFields = this.fieldsRepo.getAllDeviceFields(devicesId);
        List<MeasurementDTO> measurementList = new ArrayList<>();

        for (Fields f : deviceFields) {
            if (f.getFieldType().equals("1")) {
                p = PageRequest.of(0, 20);
                measurementList.addAll(this.measurementRepo.getDevicesMeasurementByFields(devicesId, f.getFieldKey(), p));
            } else {
                p = PageRequest.of(0, 1);
                measurementList.addAll(this.measurementRepo.getDevicesMeasurementByFields(devicesId, f.getFieldKey(), p));
            }
        }

        for (int i = 0; i < deviceFields.size(); i++) {
            for (MeasurementDTO mes : measurementList) {
                if (deviceFields.get(i).getFieldKey().equals(mes.getPayloadKey())) {
                    mes.setFieldType(deviceFields.get(i).getFieldType());
                }
            }
        }

        return measurementList;
    }

    public Measurement getMeasurementByField(int devicesId, String fieldKey) {
        List<Measurement> mesList = this.measurementRepo.getMeasurementByField(devicesId,fieldKey, PageRequest.of(0,1));
        Measurement measurement;
        if(!mesList.isEmpty() && mesList.get(0) != null){
            measurement = mesList.get(0);
        }else {
            measurement = new Measurement(0,fieldKey,"0","0");
        }
        return measurement;
    }

    public List<Measurement> getMeasurementListByField(int devicesId, String fieldKey) {
        List<Measurement> mesList = this.measurementRepo.getMeasurementByField(devicesId,fieldKey, PageRequest.of(0,20));
        return mesList;
    }

    public Map<String,List<Measurement>> getMapOfListMeasurment(int devicesId) {
        List<Fields> fieldsList = this.fieldsRepo.getAllDeviceFields(devicesId);
        Map<String,List<Measurement>> measuremntLoL = new HashMap<>();

        for (Fields f: fieldsList){
            List<Measurement> measurementList = this.measurementRepo.getMeasurementByField(devicesId,f.getFieldKey(), PageRequest.of(0,20));

            measuremntLoL.put(f.fieldTypeToString(),measurementList);
//            if(!measuremntLoL.isEmpty()){
//            }else {
//                measuremntLoL.put(f, null);
//            }
        }

        return measuremntLoL;
    }

    public List<List<MeasurementDTO>> getListOfListMeasurment(int devicesId) {
        List<Fields> fieldsList = this.fieldsRepo.getAllDeviceFields(devicesId);
        List<List<MeasurementDTO>> listOfList = new ArrayList<>();

        for (Fields f : fieldsList) {
            List<Measurement> tmp =this.measurementRepo.getMeasurementByField(devicesId,f.getFieldKey(), PageRequest.of(0,20));
            if(!tmp.isEmpty()){
                listOfList.add(convertMeasToDTO(tmp,f.getFieldType()));
            }else {
                List<MeasurementDTO> t = new ArrayList<>();
                t.add(new MeasurementDTO(0,f.getFieldKey(),"0","0", f.getFieldType()));
                listOfList.add(t);
            }
        }
        return listOfList;
    }

    public List<MeasurementDTO> convertMeasToDTO(List<Measurement> measurementList, String fieldType){
        List<MeasurementDTO> measurementDTOS = new ArrayList<>();
        for (Measurement m : measurementList){
            measurementDTOS.add(new MeasurementDTO(m.getMeasurementId(),m.getPayloadKey(),m.getPayloadValue(),m.getTime(),fieldType));
        }
        return measurementDTOS;
    }
}
