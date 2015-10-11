class RemoveColumnFromTreatment < ActiveRecord::Migration
  def change
    remove_column :treatments, :quantity, :float
    remove_column :treatments, :unit, :string
  end
end
