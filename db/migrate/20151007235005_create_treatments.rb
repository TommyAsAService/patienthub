class CreateTreatments < ActiveRecord::Migration
  def change
    create_table :treatments do |t|
      t.string :name, primary: true
      t.float :quantity
      t.boolean :isExercise
      t.string :unit

      t.timestamps null: false
    end
  end
end
