class AddColumnToDosages < ActiveRecord::Migration
  def change
    add_column :dosages, :quantity, :float
    add_column :dosages, :unit, :string
  end
end
