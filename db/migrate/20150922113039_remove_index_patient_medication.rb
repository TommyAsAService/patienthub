class RemoveIndexPatientMedication < ActiveRecord::Migration
  def change
    remove_index(:patient_medications, :name => 'index_patient_medications_on_medication_id')
  end
end
