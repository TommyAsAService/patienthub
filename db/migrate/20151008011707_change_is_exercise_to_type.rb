class ChangeIsExerciseToType < ActiveRecord::Migration
  def change
    remove_column :treatments, :isExercise
    add_column :treatments, :type, :string
  end
end
