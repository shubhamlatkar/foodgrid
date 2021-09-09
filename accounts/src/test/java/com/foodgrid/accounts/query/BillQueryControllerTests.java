package com.foodgrid.accounts.query;

import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import com.foodgrid.accounts.query.internal.rest.BillQueryController;
import com.foodgrid.accounts.query.internal.service.BillQueryService;
import com.foodgrid.accounts.shared.model.AddressDetails;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.accounts.shared.model.Location;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import com.foodgrid.accounts.shared.payload.ItemsEventDTO;
import com.foodgrid.accounts.shared.utility.BillActivities;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BillQueryController.class})
@AutoConfigureWebTestClient
class BillQueryControllerTests {

    @MockBean
    private BillQueryService billQueryService;

    @Autowired
    private BillQueryController billQueryController;

    @Test
    @DisplayName("Tests getBillById method of BillQueryController controller")
    void getBillById() {
        var address = new AddressResponse(
                "1",
                "1",
                new Location(12.2, 12.2),
                "test",
                new AddressDetails("addressLineOne", "addressLineTwo", "123456", "city", "state"),
                false
        );
        var billRequest = new BillRequest(List.of("1"), "1", "1");
        var billCmdModel = new BillCommandModel(billRequest, address, "test");
        billCmdModel.setId("1");
        billCmdModel.setCost(new Cost(1233, 345, 32, 900, 45));
        var itemModel = new ItemModel("1", "test", 12.34, 1);
        billCmdModel.addItem(itemModel);
        billCmdModel.removeItem(new ItemRequest("1", "1"));
        billCmdModel.addItem(itemModel);

        var event = new BillEventDTO(BillActivities.REMOVE_ITEM, billCmdModel, new ItemsEventDTO(itemModel));
        var billQuery = new BillQueryModel(event);
        var result = new BindingResults();
        when(billQueryService.getBill(anyString())).thenReturn(billQuery);

        Assertions.assertNotNull(billQueryController.getBillById("1"));
    }

}

class BindingResults implements BindingResult {

    @Override
    public Object getTarget() {
        return null;
    }

    @NotNull
    @Override
    public Map<String, Object> getModel() {
        return null;
    }

    @Override
    public Object getRawFieldValue(String s) {
        return null;
    }

    @Override
    public PropertyEditor findEditor(String s, Class<?> aClass) {
        return null;
    }

    @Override
    public PropertyEditorRegistry getPropertyEditorRegistry() {
        return null;
    }

    @Override
    public String[] resolveMessageCodes(String s) {
        return new String[0];
    }

    @Override
    public String[] resolveMessageCodes(String s, String s1) {
        return new String[0];
    }

    @Override
    public void addError(ObjectError objectError) {

    }

    @Override
    public String getObjectName() {
        return null;
    }

    @Override
    public void setNestedPath(String s) {

    }

    @Override
    public String getNestedPath() {
        return null;
    }

    @Override
    public void pushNestedPath(String s) {

    }

    @Override
    public void popNestedPath() throws IllegalStateException {

    }

    @Override
    public void reject(String s) {

    }

    @Override
    public void reject(String s, String s1) {

    }

    @Override
    public void reject(String s, Object[] objects, String s1) {

    }

    @Override
    public void rejectValue(String s, String s1) {

    }

    @Override
    public void rejectValue(String s, String s1, String s2) {

    }

    @Override
    public void rejectValue(String s, String s1, Object[] objects, String s2) {

    }

    @Override
    public void addAllErrors(Errors errors) {

    }

    @Override
    public boolean hasErrors() {
        return false;
    }

    @Override
    public int getErrorCount() {
        return 0;
    }

    @Override
    public List<ObjectError> getAllErrors() {
        return null;
    }

    @Override
    public boolean hasGlobalErrors() {
        return false;
    }

    @Override
    public int getGlobalErrorCount() {
        return 0;
    }

    @Override
    public List<ObjectError> getGlobalErrors() {
        return null;
    }

    @Override
    public ObjectError getGlobalError() {
        return null;
    }

    @Override
    public boolean hasFieldErrors() {
        return false;
    }

    @Override
    public int getFieldErrorCount() {
        return 0;
    }

    @Override
    public List<FieldError> getFieldErrors() {
        return null;
    }

    @Override
    public FieldError getFieldError() {
        return null;
    }

    @Override
    public boolean hasFieldErrors(String s) {
        return false;
    }

    @Override
    public int getFieldErrorCount(String s) {
        return 0;
    }

    @Override
    public List<FieldError> getFieldErrors(String s) {
        return null;
    }

    @Override
    public FieldError getFieldError(String s) {
        return null;
    }

    @Override
    public Object getFieldValue(String s) {
        return null;
    }

    @Override
    public Class<?> getFieldType(String s) {
        return null;
    }
}
