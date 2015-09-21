class CreatePatients < ActiveRecord::Migration
  def change
    create_table :patients do |t|
      t.string :name
      t.integer :ages
      t.string :address
      t.string :contact_number
      t.string :patient_number

      t.timestamps null: false
    end
  end
end
