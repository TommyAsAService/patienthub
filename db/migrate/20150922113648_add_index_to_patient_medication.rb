class AddIndexToPatientMedication < ActiveRecord::Migration
  def change
    add_index :patient_medications, :medication_name
  end
end
