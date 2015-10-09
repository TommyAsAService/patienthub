class CreateFeedbacks < ActiveRecord::Migration
  def change
    create_table :feedbacks do |t|
      t.references :dosage, index: true
      t.boolean :taken
      t.string :comment

      t.timestamps null: false
    end
    add_foreign_key :feedbacks, :dosages
  end
end
