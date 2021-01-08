package insertData;

class Person {
    private String country;

    private String name;

    private Character sex;

    private Integer age;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:" + name + "sex:" + sex + "age" + age;
    }

    public static Person valueOf(String country, String name) {
        Person person = new Person();
        person.country = country;
        person.name = name;
        return person;
    }

    public static Person valueOf(String name, Character sex, Integer age) {
        Person person = new Person();
        person.name = name;
        person.sex = sex;
        person.age = age;
        return person;
    }
}