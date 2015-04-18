package cs4295.cs4295_project;

/**
 * Created by Wang on 18/4/2015.
 */
public interface TimerActivity {
    //for switching images
    public int[] imgNum = {
            R.drawable.action1,R.drawable.action2,R.drawable.action3,R.drawable.action4,
            R.drawable.action5,R.drawable.action6,R.drawable.action7,R.drawable.action8,
            R.drawable.action9,R.drawable.action10,R.drawable.action11,R.drawable.action12
    };

    public String[] actionName = {
        "Jumping Jacks", "Wall Sit","Push Up","Abdominal Crunch","Step up Onto Chair",
        "Squat","Triceps Dip On Chair", "Plank", "High Knees/Running", "Lunge",
        "Push-Up and Rotation", "Right Side Plank", "Left Side Plank"
    };

}
