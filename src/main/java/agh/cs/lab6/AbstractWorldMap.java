package agh.cs.lab6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    //protected List<Animal> animals = new ArrayList<>();
    private int width;
    private int height;
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    public  AbstractWorldMap(int width,int height){
        this.width = width;
        this.height = height;
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal tmp = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, tmp);
    }
    public boolean canMoveTo(Vector2d position) {
        if (isOccupied(position)){
            //  System.out.println("Zwierze nie moze sie ruszyc na to miejsce - jest ono zajete przez inne zwierze");
            return false;
        }
        else {
            return true;
        }
    }
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            //animals.add(animal);
            animal.addObserver(this);
            animals.put(animal.getPosition(),animal);

            return true;
        }
        else {
            throw new IllegalArgumentException("Can't place animal on: "+animal.getPosition().toString());
            //return false;
        }
    }

    @Override
    public void run(MoveDirection[] directions) {

        Collection<Animal> value = animals.values();
        ArrayList<Animal> listOfAnimals = new ArrayList<>(value);

        for (int i = 0; i < directions.length; i++) {
            Vector2d v = listOfAnimals.get(i % listOfAnimals.size()).getPosition();
            listOfAnimals.get(i % listOfAnimals.size()).move(directions[i]);
            /*animals.remove(v);
            animals.put(listOfAnimals.get(i % listOfAnimals.size()).getPosition(), listOfAnimals.get(i % listOfAnimals.size()));*/
            //positionChanged(v,listOfAnimals.get(i % listOfAnimals.size()).getPosition());
        }

    }
    public boolean isOccupied(Vector2d position) {
        if(animals.get(position) == null){
            return false;
        }
        else {
            return true;
        }
        /*for (Animal animal : animals) {
            if (position.equals(animal.getPosition())) {
                return true;
            }
        }
        return false;*/
    }
    public Object objectAt(Vector2d position) {
        return animals.get(position);
        /*for (Animal animal : animals) {
            if (position.equals(animal.getPosition())) {
                return animal;
            }
        }
        return null;*/
    }
    public int getHeight(){return this.height;}
    public int getWidth(){return  this.width;}
    public String toString() {
        return new MapVisualizer(this).draw(new Vector2d(0,0),new Vector2d(width-1,height-1));
    }
}
