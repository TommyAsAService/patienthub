class CreatePatientMedications < ActiveRecord::Migration
  def change
    create_table :patient_medications do |t|
      t.string :label
      t.datetime :start_time
      t.references :medication, index: true
      t.references :patient, index: true
      t.string :notes
      t.integer :time_take_per_day
      t.integer :amount_given

      t.timestamps null: false
    end
    add_foreign_key :patient_medications, :medications
    add_foreign_key :patient_medications, :patients
  end
end
