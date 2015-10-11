class RemoveColumnFromTreatments < ActiveRecord::Migration
  def change
    remove_column :treatments, :type, :string
    add_column :treatments, :treatment_type, :string
  end
end
