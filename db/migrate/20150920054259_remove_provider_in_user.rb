class RemoveProviderInUser < ActiveRecord::Migration
  def change
    remove_column :users, :provider
  end
end
