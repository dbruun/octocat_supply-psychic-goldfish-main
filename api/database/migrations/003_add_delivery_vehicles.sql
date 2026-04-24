-- Migration 003: Add delivery_vehicles table

CREATE TABLE IF NOT EXISTS delivery_vehicles (
    vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT,
    branch_id INTEGER NOT NULL,
    vehicle_type TEXT NOT NULL,
    capacity REAL NOT NULL CHECK (capacity > 0),
    status TEXT NOT NULL DEFAULT 'available' CHECK (status IN ('available', 'in-transit', 'maintenance')),
    FOREIGN KEY (branch_id) REFERENCES branches(branch_id) ON DELETE CASCADE
);

-- Create indexes for common queries
CREATE INDEX IF NOT EXISTS idx_delivery_vehicles_branch_id ON delivery_vehicles(branch_id);
CREATE INDEX IF NOT EXISTS idx_delivery_vehicles_status ON delivery_vehicles(status);
