package map.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;

public class ObjectMapperTest {

    private ObjectMapper objMapper;

    private Map<String, Class<?>> objectMapping;

    @Before
    public void setUpBeforeTest() {
        this.objMapper = new ObjectMapper();
        this.objectMapping = this.getBasicMap();
    }

    @Test
    public void mapDataToObjectWithSimpleData() throws Exception {
        String className = "TestClass";
        this.objectMapping = this.addSimpleFields(this.objectMapping);
        Class<?> mappedClass = this.objMapper.generate(className, this.objectMapping);

        Object newMappedObject = mappedClass.newInstance();

        assertEquals(
                "New Class name should be TestClass",
                className,
                newMappedObject.getClass().getName()
                );

        Integer accountNumCheck = 5;
        String accountNameCheck = "John";
        Date currentDate = new Date();
        mappedClass.getMethod(
                "setAccountNum",
                Integer.class)
            .invoke(
                    newMappedObject,
                    accountNumCheck
                    );
        mappedClass.getMethod(
                "setAccountName",
                String.class)
            .invoke(
                newMappedObject,
                accountNameCheck
                );
        mappedClass.getMethod(
                "setAccountDOB",
                Date.class)
            .invoke(
                    newMappedObject,
                    currentDate
                );

        assertEquals(
                "The class should return 5 for account number",
                accountNumCheck,
                (Integer) mappedClass.getMethod("getAccountNum")
                .invoke(newMappedObject)
                );
        assertEquals(
                "The class should return 'John' for account name",
                accountNameCheck,
                (String) mappedClass.getMethod("getAccountName")
                .invoke(newMappedObject)
                );
        assertEquals(
                "The class should return the current Date as the Date of Birth",
                currentDate,
                (Date) mappedClass.getMethod("getAccountDOB")
                .invoke(newMappedObject)
                );
    }

    private Map<String, Class<?>> getBasicMap() {
        return new HashMap<String, Class<?>>();
    }

    private Map<String, Class<?>> addSimpleFields(Map <String, Class<?>> mapping) {
        mapping.put("accountNum", Integer.class);
        mapping.put("accountName", String.class);
        mapping.put("accountDOB", Date.class);

        return mapping;
    }

}
