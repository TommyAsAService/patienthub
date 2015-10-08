class CreateDosages < ActiveRecord::Migration
  def change
    create_table :dosages do |t|
      t.references :patient, index: true
      t.string :time_taken
      t.integer :frequency
      t.date :start_date

      t.timestamps null: false
    end
    add_foreign_key :dosages, :patients
  end
end
