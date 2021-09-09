package com.foodgrid.user.command.internal.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.user.command.internal.event.broker.AddressEventBroker;
import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.command.internal.payload.dto.response.AddressOperationSuccess;
import com.foodgrid.user.command.internal.repository.AddressCommandRepository;
import com.foodgrid.user.shared.model.AddressDetails;
import com.foodgrid.user.shared.payload.AddressEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@Slf4j
public class AddressCommandService {
    private final AddressCommandRepository addressRepository;
    private final AddressEventBroker addressEventBroker;
    private final UserSession userSession;

    @Autowired
    public AddressCommandService(AddressCommandRepository addressRepository, AddressEventBroker addressEventBroker, UserSession userSession) {
        this.addressRepository = addressRepository;
        this.addressEventBroker = addressEventBroker;
        this.userSession = userSession;
    }

    public AddressOperationSuccess addAddress(AddressRequest address, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid address");
        else {
            String id;
            if (Boolean.FALSE.equals(address.getIsSelected()) && addressRepository.findByUserId(userSession.getUserId()).isPresent())
                address.setIsSelected(true);
            try {
                var addressCommand = new AddressCommandModel(address, userSession.getUserId());
                id = addressRepository.save(addressCommand).getId();
                var addressEvent = new AddressEventDto(address, userSession.getUserId(), id, CrudActions.ADD);
                addressEventBroker.sendAddressEvent(addressEvent);
            } catch (Exception e) {
                throw new InternalServerErrorException("Internal server error for address repository");
            }
            if (id != null) {
                log.info("Address Command added address :{}", address);
                return new AddressOperationSuccess(id, "Address added successfully");
            } else
                throw new InvalidDataException("Invalid data");
        }
    }

    public AddressOperationSuccess deleteAddressById(String addressId) {
        try {
            addressRepository.deleteById(addressId);
            var addressDto = new AddressEventDto();
            addressDto.setAction(CrudActions.DELETE);
            addressDto.setId(addressId);
            addressEventBroker.sendAddressEvent(addressDto);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid address id");
        }
        log.info("Address Command deleted address by id :{}", addressId);
        return new AddressOperationSuccess(addressId, "Deleted successfully");
    }

    public AddressOperationSuccess patchAddress(String addressId, AddressRequest address, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid address");
        var existingAddress = addressRepository.findById(addressId).orElse(null);
        if (existingAddress != null) {
            existingAddress.setAddressDetails(new AddressDetails(address.getAddressLineOne(), address.getAddressLineTwo(), address.getPin(), address.getCity(), address.getState()));
            existingAddress.setName(address.getName());
            existingAddress.setLocation(address.getLocation());
            existingAddress.setIsSelected(address.getIsSelected());
            addressRepository.save(existingAddress);
            var addressEvent = new AddressEventDto(existingAddress, CrudActions.UPDATE);
            addressEventBroker.sendAddressEvent(addressEvent);
            log.info("Address Command update address :{}", address);
            return new AddressOperationSuccess(addressId, "Address updated successfully");
        } else return new AddressOperationSuccess(addressId, "Not Found");
    }
}
