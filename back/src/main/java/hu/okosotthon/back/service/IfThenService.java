package hu.okosotthon.back.service;

import hu.okosotthon.back.dto.IfThenDTO;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.IfThen;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.repository.DeviceRepo;
import hu.okosotthon.back.repository.FieldsRepo;
import hu.okosotthon.back.repository.IfThenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IfThenService {
    private IfThenRepo ifThenRepo;
    private DeviceRepo deviceRepo;
    private FieldsRepo fieldsRepo;

    @Autowired
    public IfThenService(IfThenRepo ifThenRepo, DeviceRepo deviceRepo, FieldsRepo fieldsRepo) {
        this.ifThenRepo = ifThenRepo;
        this.deviceRepo = deviceRepo;
        this.fieldsRepo = fieldsRepo;
    }

    public IfThen saveIfThen(IfThen ifThen) {
        return this.ifThenRepo.save(ifThen);
    }

    public List<IfThenDTO> getIfThen(int deviceId) {
        List<IfThen> ifThenList = this.ifThenRepo.findAllByIfDeviceId(deviceId);
        List<Devices> ifDevicesList = new ArrayList<>();
        List<Devices> thenDevicesList = new ArrayList<>();
        List<Fields> ifFieldList = new ArrayList<>();
        List<Fields> thenFieldList = new ArrayList<>();
        for (IfThen i: ifThenList) {
            ifDevicesList.add(this.deviceRepo.getDeviceNameAndIdByDeviceId(i.getIfDeviceId()));
            thenDevicesList.add(this.deviceRepo.getDeviceNameAndIdByDeviceId(i.getThenDeviceId()));
            ifFieldList.add(this.fieldsRepo.getFieldsById(i.getIfFieldId()));
            thenFieldList.add(this.fieldsRepo.getFieldsById(i.getThenFieldId()));
        }

        List<IfThenDTO> ifThenDTOList = new ArrayList<>();
        for (int i = 0; i < ifThenList.size(); i++) {
            ifThenDTOList.add(
                    new IfThenDTO(
                            ifThenList.get(i).getIfThenId(),
                            ifDevicesList.get(i).getDevicesId(),
                            ifDevicesList.get(i).getDeviceName(),
                            thenDevicesList.get(i).getDevicesId(),
                            thenDevicesList.get(i).getDeviceName(),
                            ifFieldList.get(i).getFieldId(),
                            ifFieldList.get(i).getFieldKey(),
                            thenFieldList.get(i).getFieldId(),
                            thenFieldList.get(i).getFieldKey(),
                            ifThenList.get(i).getAfter(),
                            ifThenList.get(i).getMessage()
                    ));
        }
        return ifThenDTOList;
    }
}
