class CreateQuizFeedbacks < ActiveRecord::Migration
  def change
    create_table :quiz_feedbacks do |t|
      t.string :question
      t.string :answer
      t.references :patient, index: true

      t.timestamps null: false
    end
    add_foreign_key :quiz_feedbacks, :patients
  end
end
