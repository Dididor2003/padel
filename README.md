<h1>Normes de commits del projecte</h1>

Abans d’iniciar el desenvolupament del projecte, establim les següents normes per al control de versions amb Git, amb l’objectiu de mantenir un treball ordenat i fàcil de seguir.
<ol>
<li>Cada commit ha de correspondre a una funcionalitat concreta o a una part específica del projecte. No es barrejaren diferents responsabilitats dins del mateix commit.</li>

<li>El desenvolupament seguirà l’estructura per capes del backend: models, repositoris, excepcions, seguretat, DTOs, serveis i controllers. Els commits respectaran aquest ordre per mantenir una evolució coherent del projecte.</li>

<li>Els missatges dels commits han de ser clars i descriptius, indicant què s’ha implementat o modificat i a quina capa afecta.</li>

<li>Els canvis de configuració (com ajustos al pom.xml o a l’arxiu application.properties) es faran en commits separats de la implementació de funcionalitats.</li>

<li>Les millores de codi, refactoritzacions o neteges s’han de registrar en commits independents, diferenciant-les de la creació de noves funcionalitats.</li>

<li>No es faran commits amb missatges genèrics o poc descriptius. Cada missatge ha de permetre entendre fàcilment el canvi realitzat.</li>
</ol>

<footer>
    <em>Fet per <strong>Adam Espilla Ruiz</strong> i <strong>Didac Queija Segovia</strong> :)</em>
</footer>
