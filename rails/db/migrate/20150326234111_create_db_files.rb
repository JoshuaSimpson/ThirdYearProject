class CreateDbFiles < ActiveRecord::Migration
  def change
    create_table :db_files do |t|

      t.timestamps
    end
  end
end
