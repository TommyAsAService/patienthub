class AddEmailToProviderInUser < ActiveRecord::Migration
  def change
    change_column :users, :provider, :string, null: false
  end
end
