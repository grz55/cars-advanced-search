package com.grz55.cars.gui;

import com.grz55.cars.controller.CarController;
import com.grz55.cars.model.Car;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("gui/cars")
public class CarGui extends VerticalLayout {

    private TextField textFieldBrand;
    private TextField textFieldModel;
    private IntegerField integerFieldPageNumber;
    private IntegerField integerFieldTopSpeedFrom;
    private IntegerField integerFieldTopSpeedTo;
    private IntegerField integerFieldProductionYearFrom;
    private IntegerField integerFieldProductionYearTo;
    private IntegerField integerFieldMileageFrom;
    private IntegerField integerFieldMileageTo;
    private IntegerField integerFieldPriceFrom;
    private IntegerField integerFieldPriceTo;
    private IntegerField integerFieldPageSize;

    private HorizontalLayout horizontalLayoutSearchOptions;
    private HorizontalLayout horizontalLayoutAdvancedSearchOptions;
    private HorizontalLayout horizontalLayoutPagingOptions;
    private HorizontalLayout horizontalLayoutButtons;

    ComboBox<String> sortByComboBox;
    ComboBox<String> sortOrderComboBox;

    private Button buttonSearch;
    private Button buttonClear;

    private CarController carController;
    private Grid<Car> carsGrid;

    @Autowired
    public CarGui(CarController carController) {
        init(carController);
    }

    private void init(CarController carController) {
        this.carController = carController;
        textFieldBrand = new TextField("Brand:");
        textFieldModel = new TextField("Model:");
        integerFieldTopSpeedFrom = new IntegerField("Min top speed:");
        integerFieldTopSpeedTo = new IntegerField("Max top speed:");
        integerFieldProductionYearFrom = new IntegerField("Min production year:");
        integerFieldProductionYearTo = new IntegerField("Max production year:");
        integerFieldMileageFrom = new IntegerField("Min mileage:");
        integerFieldMileageTo = new IntegerField("Max mileage:");
        integerFieldPriceFrom = new IntegerField("Min price:");
        integerFieldPriceTo = new IntegerField("Max price:");
        sortByComboBox = new ComboBox<>("Sort by:");
        sortOrderComboBox = new ComboBox<>("Sort order:");

        integerFieldPageSize = new IntegerField("Page size:");
        integerFieldPageSize.setMin(10);
        integerFieldPageSize.setHasControls(true);
        integerFieldPageSize.setMax(50);
        integerFieldPageSize.setStep(10);
        integerFieldPageSize.setValue(10);

        integerFieldPageNumber = new IntegerField("Page number:");
        integerFieldPageNumber.setValue(1);
        integerFieldPageNumber.setMin(1);
        integerFieldPageNumber.setHasControls(true);

        sortByComboBox.setItems("Brand", "Model", "Top Speed", "Production Year", "Mileage", "Price");
        sortByComboBox.setValue("Brand");
        sortOrderComboBox.setItems("ASC", "DESC");
        sortOrderComboBox.setValue("ASC");


        buttonSearch = new Button("Find cars");
        buttonClear = new Button("Clear search criteria");
        
        carsGrid = new Grid<>(Car.class);
        horizontalLayoutSearchOptions = new HorizontalLayout();
        horizontalLayoutAdvancedSearchOptions = new HorizontalLayout();
        horizontalLayoutPagingOptions = new HorizontalLayout();
        horizontalLayoutButtons = new HorizontalLayout();

        carsGrid.removeColumnByKey("id");
        carsGrid.setColumns("brand", "model", "topSpeed", "productionYear", "mileage", "price");

        initActions();

        horizontalLayoutSearchOptions.add(textFieldBrand, textFieldModel);
        horizontalLayoutAdvancedSearchOptions.add(integerFieldTopSpeedFrom, integerFieldTopSpeedTo, integerFieldProductionYearFrom, integerFieldProductionYearTo, integerFieldMileageFrom, integerFieldMileageTo, integerFieldPriceFrom, integerFieldPriceTo);
        horizontalLayoutPagingOptions.add(integerFieldPageSize, integerFieldPageNumber, sortByComboBox, sortOrderComboBox);
        horizontalLayoutButtons.add(buttonSearch, buttonClear);
        add(horizontalLayoutSearchOptions, horizontalLayoutAdvancedSearchOptions, horizontalLayoutPagingOptions, horizontalLayoutButtons, carsGrid);
    }

    private void initActions() {
        buttonSearch.addClickListener(buttonClickEvent -> {
            if (integerFieldPageNumber.getValue() >= 1) {
                List<Car> carsByGivenParamsFound = carController.getCarsByGivenParams(textFieldBrand.getOptionalValue(), textFieldModel.getOptionalValue(), integerFieldTopSpeedFrom.getOptionalValue(), integerFieldTopSpeedTo.getOptionalValue(), integerFieldProductionYearFrom.getOptionalValue(), integerFieldProductionYearTo.getOptionalValue(), integerFieldMileageFrom.getOptionalValue(), integerFieldMileageTo.getOptionalValue(), integerFieldPriceFrom.getOptionalValue(), integerFieldPriceTo.getOptionalValue(), integerFieldPageNumber.getValue() - 1, integerFieldPageSize.getValue(), sortByComboBox.getValue(), sortOrderComboBox.getValue());
                carsGrid.setItems(carsByGivenParamsFound);
            }
        });

        buttonClear.addClickListener(buttonClickEvent -> {
            textFieldBrand.clear();
            textFieldModel.clear();
            integerFieldTopSpeedFrom.clear();
            integerFieldTopSpeedTo.clear();
            integerFieldProductionYearFrom.clear();
            integerFieldProductionYearTo.clear();
            integerFieldMileageFrom.clear();
            integerFieldMileageTo.clear();
            integerFieldPriceFrom.clear();
            integerFieldPriceTo.clear();
        });
    }

}
