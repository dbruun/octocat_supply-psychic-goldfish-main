# OctoCAT Supply — Product Vision

## Problem Statement

Supply chain operations today rely on fragmented tools — spreadsheets for inventory, email for supplier communication, manual processes for order tracking, and siloed systems that don't talk to each other. Teams waste hours reconciling data across systems, orders slip through the cracks, and visibility into the end-to-end supply chain is limited to whoever happens to be looking at the right spreadsheet at the right time.

## Target Users

| Persona | Role | Key Need |
|---------|------|----------|
| **Supply Chain Manager** | Oversees procurement, inventory, and fulfillment | End-to-end visibility across suppliers, orders, and deliveries |
| **Branch Operator** | Manages a local branch's inventory and orders | Real-time stock levels, order placement, delivery tracking |
| **Procurement Specialist** | Manages supplier relationships and contracts | Supplier performance metrics, order history, contract compliance |
| **Logistics Coordinator** | Schedules and tracks deliveries | Vehicle assignment, route optimization, delivery status updates |

## Product Vision

**OctoCAT Supply** is a modern supply chain management platform that unifies supplier management, order processing, inventory tracking, and delivery logistics into a single, real-time application. Built on a clean REST API with a responsive web interface, it gives every stakeholder — from branch operators to supply chain directors — the visibility and control they need to keep operations running smoothly.

## Key Capabilities

### Core (v1)
- **Supplier Management** — Maintain a registry of suppliers with contact details, status tracking, and performance history
- **Product Catalog** — Centralized product catalog with pricing, categories, and supplier associations
- **Order Processing** — Create, track, and fulfill orders across branches with line-item detail and delivery linkage
- **Branch Operations** — Manage headquarters and branch locations with hierarchical organization
- **Delivery Tracking** — Track deliveries from supplier to branch with status updates and vehicle assignment

### Planned (v2)
- **Inventory Levels** — Real-time stock quantities per product per branch with low-stock alerts
- **Delivery Vehicle Management** — Track vehicles assigned to branches with type, capacity, and availability status
- **Dashboard & Analytics** — Operational dashboards showing order volume, delivery performance, and supplier reliability
- **User Profiles** — Role-based access with personal dashboards and notification preferences

### Future Considerations
- **Mobile Experience** — Responsive or native mobile app for drivers and branch operators in the field
- **Event-Driven Architecture** — Pub/sub for real-time updates (order placed → supplier notified → delivery scheduled)
- **Multi-Tenant Support** — Tenant isolation for SaaS deployment across multiple organizations
- **AI-Assisted Operations** — Predictive reordering, anomaly detection, demand forecasting

## Architecture Summary

See [`architecture.md`](architecture.md) for the full technical architecture. In brief:

- **Backend:** Spring Boot REST API with JPA entities and SQLite persistence
- **Frontend:** React 18+ with TypeScript, Vite, and Tailwind CSS
- **Infrastructure:** Docker Compose for local dev, Bicep + GitHub Actions for Azure deployment
- **Data Model:** ERD-driven design with Suppliers, Products, Orders, OrderDetails, Deliveries, Branches, and Headquarters

## Success Metrics

| Metric | Target | How We Measure |
|--------|--------|---------------|
| **API Coverage** | 100% of entities have full CRUD endpoints with Swagger docs | Automated Swagger validation |
| **Test Coverage** | ≥ 80% unit test coverage on API routes | Maven Surefire + JaCoCo reports |
| **Build Reliability** | CI passes on every PR merge to `main` | GitHub Actions status checks |
| **Security Posture** | Zero unresolved high/critical CodeQL alerts on `main` | GHAS dashboard |
| **Deployment Cadence** | Deployable to staging within 5 minutes of merge | GitHub Actions deployment workflow |

## Non-Goals

- This is **not** a full ERP system — we don't handle accounting, HR, or manufacturing
- This is **not** a marketplace — there is no self-service supplier onboarding or public-facing storefront
- We do **not** aim for offline-first mobile support in v1
- We do **not** replace existing warehouse management systems (WMS) — we integrate with them

## Competitive Landscape

OctoCAT Supply is a demo application designed to showcase modern development practices. In a real-world context, it would compete with:
- SAP Integrated Business Planning
- Oracle Supply Chain Management Cloud
- Microsoft Dynamics 365 Supply Chain Management

Our differentiator is **developer experience** — the entire application is built, tested, and deployed using AI-assisted workflows with GitHub Copilot, demonstrating how modern teams can ship faster with higher quality.
