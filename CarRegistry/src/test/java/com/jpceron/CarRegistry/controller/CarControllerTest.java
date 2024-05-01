package com.jpceron.CarRegistry.controller;

import com.jpceron.CarRegistry.domain.Car;
import com.jpceron.CarRegistry.service.BrandService;
import com.jpceron.CarRegistry.service.CarService;
import com.jpceron.CarRegistry.service.impl.JwtService;
import com.jpceron.CarRegistry.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CarService carService;

    @MockBean
    private BrandService brandService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); // Inicializar mockMvc en setUp()
    }



    @Test
    @WithMockUser(username = "juan", password = "12345",roles = {"CLIENT"})
    void test_getCarId() throws Exception {

        Car car = new Car();
        car.setModel("Focus");



        when(carService.getCarById(16)).thenReturn(car);



        this.mockMvc.perform(MockMvcRequestBuilders.get("/carservice/car/16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Focus"));
    }

}