class AddIndexPatientMedication < ActiveRecord::Migration
  def change
    remove_column :patient_medications, :medication_id
    add_column :patient_medications, :medication_name, :string
  end
end
