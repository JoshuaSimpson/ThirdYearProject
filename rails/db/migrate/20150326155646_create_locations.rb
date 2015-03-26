class CreateLocations < ActiveRecord::Migration
  def change
    create_table :locations do |t|
      t.string :loc
      t.datetime :time

      t.timestamps
    end
  end
end
