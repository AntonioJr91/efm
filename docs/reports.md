# Relatórios Essenciais para o EFM

> Classificados por **prioridade de aprendizado** e **valor para o domínio**.

---

## 📦 1. Relatório de Estoque

**Por quê?** Exercita queries de agregação simples e projeções JPA.

| Tipo | O que mostra |
|------|-------------|
| Posição atual | Todos os produtos com estoque atual, unidade e categoria |
| Movimentações | Entradas/saídas (`StockMovement`) filtradas por produto e período |
| Estoque crítico | Produtos abaixo de um limite mínimo configurável |

**Conceitos aprendidos:** `@Query` JPQL, `Projection interfaces`, filtro por período (`LocalDate`).

---

## 🌾 2. Relatório de Produção

**Por quê?** Exercita `GROUP BY`, `SUM` e relacionamento entre entidades (`Production → areaId, productId`).

| Tipo | O que mostra |
|------|-------------|
| Por produto | Total produzido por produto em um período |
| Por área | Total produzido agrupado por área |
| Linha do tempo | Produção diária/mensal para visualizar tendências |

**Conceitos aprendidos:** JPQL com agregação (`SUM`, `GROUP BY`), DTOs de projeção com `new` no JPQL.

---

## 📋 3. Relatório de Ordens de Serviço

**Por quê?** Entidade mais rica do domínio — tem status, funcionário, área, itens e datas.

| Tipo | O que mostra |
|------|-------------|
| Por status | Quantitativo de OS por status (`IN_PROGRESS`, `COMPLETED`, `CANCELED`) |
| Por funcionário | OS atribuídas/concluídas por funcionário num período |
| Por categoria de serviço | OS agrupadas por `ServiceCategory` (PLANTING, HARVEST, etc.) |
| Por área | OS por área da fazenda |

**Conceitos aprendidos:** Relacionamentos `@ManyToOne` em queries, `Enum` como filtro, paginação com `Pageable`.

---

## 👷 4. Relatório de Funcionários

**Por quê?** Fácil de implementar e introduz **subquery** e filtros compostos.

| Tipo | O que mostra |
|------|-------------|
| Ativos vs inativos | Contagem por `Status` |
| Por cargo | Distribuição por `JobRole` |
| Produtividade | Nº de OS concluídas por funcionário num período (JOIN com `ServiceOrder`) |

**Conceitos aprendidos:** `JOIN` entre `Employee` e `ServiceOrder`, contagens condicionais (`CASE WHEN`), relatório com múltiplas fontes.

---

## 🗺️ 5. Relatório por Área (Dashboard Operacional)

**Por quê?** Exercita a visão consolidada — ideal para praticar `@Query` nativas e DTOs complexos.

| Tipo | O que mostra |
|------|-------------|
| Resumo por área | OS abertas, concluídas, produção total, produtos mais usados |

**Conceitos aprendidos:** Native query, DTO com múltiplos campos agregados, `@SqlResultSetMapping` ou `Tuple`.

---

## 🛠️ Sugestão de Implementação Técnica

### Endpoint padrão para relatórios
```
GET /reports/stock
GET /reports/stock/critical
GET /reports/productions?startDate=&endDate=&groupBy=product
GET /reports/service-orders?status=&employeeId=&startDate=&endDate=
GET /reports/employees/productivity?startDate=&endDate=
GET /reports/areas/summary
```

### Stack recomendada para exportação (opcional, mas agrega muito)
| Biblioteca | Formato | Complexidade |
|-----------|---------|-------------|
| **Apache POI** | `.xlsx` | Média — ótima para dev pleno |
| **iText / OpenPDF** | `.pdf` | Média-alta |
| **JasperReports** | PDF/Excel | Alta — vale como desafio extra |

---

## 📚 Ordem de Implementação Sugerida

```
1. Relatório de Estoque (mais simples, conceitos básicos)
   ↓
2. Relatório de Produção (introduz GROUP BY / SUM)
   ↓
3. Relatório de OS por status/funcionário (relacionamentos)
   ↓
4. Relatório de Funcionários com produtividade (JOIN entre módulos)
   ↓
5. Dashboard por área (consulta nativa, DTO complexo)
   ↓
6. Exportação para Excel/PDF (desafio final)
```
