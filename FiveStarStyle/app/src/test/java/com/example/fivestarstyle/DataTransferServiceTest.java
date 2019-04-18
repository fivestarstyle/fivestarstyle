package com.example.fivestarstyle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseFirestore.class, FirebaseUser.class})
public class DataTransferServiceTest extends DataTransferService{
    //mock database reference and mock user
    private FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
    private FirebaseUser mockUser = Mockito.mock(FirebaseUser.class);

    @Test
    public void addItemTest() {

    }

    @Test
    public void deleteItemTest() {

    }

    @Test
    public void getCategoryCountTest() {

    }
}