class RemoveIndex < ActiveRecord::Migration
  def change
    remove_index :patient_medications, name: 'index_patient_medications_on_medication_name'
  end
end
