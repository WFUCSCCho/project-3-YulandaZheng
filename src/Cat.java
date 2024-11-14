/****************************************************************************
 @file: Cat.java
 @description: This program is the Cat class and has all the parameters
 @author: Yulanda Zheng
 @date: October 14, 2024
 ****************************************************************************/

public class Cat implements Comparable<Cat> {

    private String breed;
    private int age;
    private int weight;
    private String colour;
    private String gender;

    //constructor
    public Cat(){
        breed = "none";
        age = -1;
        weight = -1;
        colour = "none";
        gender = "none";
    }

    //filled constructor
    public Cat(String breed, int age, int weight, String colour, String gender){
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.colour = colour;
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int compareTo(Cat o) {
        return age-o.age;
    }

    @Override
    public String toString() { //prints out the cat info in a sentence
        String temp = Integer.toString(age)+ " year old, " + Integer.toString(weight)+ "kg, " + gender +" "+ colour +" " + breed + " kitty ";
        return temp;
    }

    public boolean isEqual(Cat o){
        boolean same = true;

        if(!this.breed.equals(o.breed)){
            same = false;
        }
        if (this.age != o.age){
            same = false;
        }
        if(this.weight != o.weight){
            same = false;
        }
        if(!this.colour.equals( o.colour)){
            same = false;
        }
        if(!this.gender.equals(o.gender)){
            same = false;
        }

        return same;
    }
}
