package com.example.a7minuteworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1, "Jumping Jacks", R.drawable.bg, false, false)
        exerciseList.add(jumpingJacks)

        val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.bg, false, false)
        exerciseList.add(wallSit)

        val pushUps = ExerciseModel(3, "Push Ups", R.drawable.bg, false, false)
        exerciseList.add(pushUps)

        val abdominalCrunch = ExerciseModel(4, "Abdominal Crunch", R.drawable.bg, false, false)
        exerciseList.add(abdominalCrunch)

        val stepUpOnChair = ExerciseModel(5, "Step Up On Chair", R.drawable.bg, false, false)
        exerciseList.add(stepUpOnChair)

        val squats = ExerciseModel(6, "Squats", R.drawable.bg, false, false)
        exerciseList.add(squats)

        val tricepDips = ExerciseModel(7, "Tricep Dips", R.drawable.bg, false, false)
        exerciseList.add(tricepDips)

        val plank = ExerciseModel(8, "Plank", R.drawable.bg, false, false)
        exerciseList.add(plank)

        val highKnees = ExerciseModel(9, "High Knees Running", R.drawable.bg, false, false)
        exerciseList.add(highKnees)

        val lunges = ExerciseModel(10, "Lunges", R.drawable.bg, false, false)
        exerciseList.add(lunges)

        val pushUpRotation = ExerciseModel(11, "Push Up Rotation", R.drawable.bg, false, false)
        exerciseList.add(pushUpRotation)

        val sidePlank = ExerciseModel(12, "Side Plank", R.drawable.bg, false, false)
        exerciseList.add(sidePlank)
         return exerciseList

    }
}