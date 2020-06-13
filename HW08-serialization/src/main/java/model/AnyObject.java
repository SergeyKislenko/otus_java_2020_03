package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AnyObject {
    private String name;
    private int age;
    private String address;
    private String hobby;
    private boolean isSportsmen;
    private List<String> education;
    private Object[] anything;

    public AnyObject(String name, int age, String address, String hobby, boolean isSportsmen, List<String> education, Object[] anything) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.hobby = hobby;
        this.isSportsmen = isSportsmen;
        this.education = education;
        this.anything = anything;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyObject anyObject = (AnyObject) o;
        return age == anyObject.age &&
                isSportsmen == anyObject.isSportsmen &&
                Objects.equals(name, anyObject.name) &&
                Objects.equals(address, anyObject.address) &&
                Objects.equals(hobby, anyObject.hobby) &&
                Objects.equals(education, anyObject.education) &&
                Arrays.equals(anything, anyObject.anything);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, age, address, hobby, isSportsmen, education);
        result = 31 * result + Arrays.hashCode(anything);
        return result;
    }

    @Override
    public String toString() {
        return "AnyObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", hobby='" + hobby + '\'' +
                ", isSportsmen=" + isSportsmen +
                ", education=" + education +
                ", anything=" + Arrays.toString(anything) +
                '}';
    }
}
