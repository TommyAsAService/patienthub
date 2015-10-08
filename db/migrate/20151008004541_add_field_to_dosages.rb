class AddFieldToDosages < ActiveRecord::Migration
  def change
    add_column :dosages, :treatment_name, :string
  end
end
