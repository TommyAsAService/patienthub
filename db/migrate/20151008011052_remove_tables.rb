class RemoveTables < ActiveRecord::Migration
  def change
    drop_table :patient_medications
    drop_table :medications
  end
end
