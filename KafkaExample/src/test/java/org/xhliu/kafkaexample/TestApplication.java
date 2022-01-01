package org.xhliu.kafkaexample;

import org.xhliu.kafkaexample.protos.gen.AddressBook;
import org.xhliu.kafkaexample.protos.gen.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestApplication {
    public static void main(String[] args) throws Exception {
        Person person = Person.newBuilder()
                .setId(1)
                .setEmail("123456789@gmail.com")
                .setName("xhliu")
                .addPhones(Person.PhoneNumber.newBuilder().setNumber("123456789").build())
                .build();

        AddressBook addressBook = AddressBook.newBuilder().addPeople(person).build();
        try (
                FileOutputStream out = new FileOutputStream("/tmp/addressBook.obj");
                FileInputStream in = new FileInputStream("/tmp/addressBook.obj")
            ){
            addressBook.writeTo(out);
//            System.out.println("序列化 AddressBook 对象成功");

            AddressBook obj = AddressBook.parseFrom(in);
            System.out.println("反序列化之后的对象：");
            System.out.println(obj.getPeople(0));
        }
    }
}
