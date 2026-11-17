package com.practicewithsanketh.SystemDesignLLD.OOPs;

/**
 * ============================================================================
 * 1. ASSOCIATION ("Uses-A" or "Communicates-With")
 * ============================================================================
 * 
 * 🧠 THE INTUITION:
 * - Two independent objects interact with each other to get work done.
 * - Neither object OWNS the other.
 * - Both have their own independent lifecycles.
 * - If one object is deleted from memory, the other object continues to live unaffected!
 * 
 * 🚗 REAL-WORLD LLD USE CASE: Doctor & Patient (or Uber Passenger & Driver)
 * - Dr. Sharma treats Patient Rahul.
 * - Dr. Sharma does NOT own Rahul. Rahul does NOT own Dr. Sharma.
 * - Tomorrow Rahul can consult Dr. Verma.
 * - If Rahul gets discharged (or leaves the hospital), Dr. Sharma continues treating others!
 */

// Independent Entity 1: Patient
class Patient {
    private final String patientId;
    private final String name;

    public Patient(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void consultDoctor(Doctor doctor) {
        System.out.println("🩺 Patient " + name + " is consulting Dr. " + doctor.getName());
        doctor.diagnose(this); // Passing self as parameter (Pure Association!)
    }
}

// Independent Entity 2: Doctor
class Doctor {
    private final String doctorId;
    private final String name;
    private final String specialization;

    public Doctor(String doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    // Association via Method Parameter: Doctor "uses" Patient temporarily
    public void diagnose(Patient patient) {
        System.out.println("📋 Dr. " + name + " (" + specialization + ") is prescribing medicines to " + patient.getName());
    }
}

public class Association {
    public static void main(String[] args) {
        System.out.println("=== REAL-WORLD LLD: ASSOCIATION DEMO ===");

        Doctor doctor = new Doctor("DOC-101", "Mehta", "Cardiologist");
        Patient patient = new Patient("PAT-501", "Aryan");

        // Both objects exist independently in memory
        patient.consultDoctor(doctor);

        // Even if patient object is nullified, doctor object lives on!
        patient = null;
        System.out.println("✅ Patient left. Doctor " + doctor.getName() + " is still ready for other patients!");
    }
}

