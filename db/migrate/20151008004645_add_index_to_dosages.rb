class AddIndexToDosages < ActiveRecord::Migration
  def change
    add_index :dosages, :treatment_name
  end
end
