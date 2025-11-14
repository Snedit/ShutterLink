package com.shutterlink.auth_service.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shutterlink.auth_service.repository.AuthRepository;


// to get all the beans, components, etc, we have to annotate it
@SpringBootTest
public class AuthServiceTests {
    
@Autowired
private AuthRepository authRepository;
    
@Test
public void testFindByUsername(){
        // expected, actual 
            
    // assertEquals(4, 2+2);
    assertNotNull(authRepository.findByEmail("sohamde2004@gmail.com"));
    // assertTrue(authRepository.existsByEmail("sohamde2004@gmail.com"));
}

@ParameterizedTest
@CsvSource({
    "1, 2, 3",
    "3, 2, 5",
    "5, 4, 9",
    "-1, 20, 19"

})
public void dynamicTesting(int a , int b, int expected){
    assertEquals(expected, a + b);
}

}
