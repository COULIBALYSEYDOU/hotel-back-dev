#!/usr/bin/env python3
"""
Script pour corriger automatiquement les champs redondants dans les entités.
Supprime les champs déjà présents dans BaseEntity.
"""

import os
import re
import sys

# Pattern pour identifier les sections à supprimer
PATTERNS_TO_REMOVE = [
    # Section Audit complète
    (r'// ========== Audit \(OBLIGATOIRE\) ==========\s*\n'
     r'(@CreatedDate\s*\n)?'
     r'@Column\(name = "created_at".*?\n'
     r'private LocalDateTime createdAt;\s*\n'
     r'(@CreatedBy\s*\n)?'
     r'@Column\(name = "created_by".*?\n'
     r'private String createdBy;\s*\n'
     r'(@LastModifiedDate\s*\n)?'
     r'@Column\(name = "modified_at".*?\n'
     r'private LocalDateTime modifiedAt;\s*\n'
     r'(@LastModifiedBy\s*\n)?'
     r'@Column\(name = "modified_by".*?\n'
     r'private String modifiedBy;\s*\n', 
     '    // Note: Audit, versioning et soft delete sont gérés par BaseEntity\n    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)\n'),
    
    # Section Optimistic Locking
    (r'// ========== Optimistic Locking \(OBLIGATOIRE\) ==========\s*\n'
     r'@Version\s*\n'
     r'@Column\(name = "version"\)\s*\n'
     r'private Long version;\s*\n',
     ''),
    
    # Section Soft Delete
    (r'// ========== Soft Delete \(OBLIGATOIRE\) ==========\s*\n'
     r'(@Builder\.Default\s*\n)?'
     r'@Column\(name = "deleted".*?\n'
     r'private Boolean deleted.*?;\s*\n'
     r'@Column\(name = "deleted_at"\)\s*\n'
     r'private LocalDateTime deletedAt;\s*\n'
     r'@Column\(name = "deleted_by".*?\n'
     r'private String deletedBy;\s*\n',
     ''),
    
    # Status enum à la fin
    (r'@Enumerated\(EnumType\.STRING\)\s*\n'
     r'private Status status = Status\.ACTIF;\s*\n',
     '    // Note: Status est géré par BaseEntity\n'),
]

# Imports à supprimer
IMPORTS_TO_REMOVE = [
    r'import org\.springframework\.data\.annotation\.CreatedBy;\s*\n',
    r'import org\.springframework\.data\.annotation\.CreatedDate;\s*\n',
    r'import org\.springframework\.data\.annotation\.LastModifiedBy;\s*\n',
    r'import org\.springframework\.data\.annotation\.LastModifiedDate;\s*\n',
    r'import jakarta\.persistence\.Version;\s*\n',
    r'import projet_hotelier\.hotel\.core\.common\.enumeration\.Status;\s*\n',
    r'import jakarta\.persistence\.EnumType;\s*\n',
    r'import jakarta\.persistence\.Enumerated;\s*\n',
]

# Méthodes à remplacer
METHOD_REPLACEMENTS = [
    (r'public void softDelete\(String deletedBy\) \{\s*\n'
     r'        this\.deleted = true;\s*\n'
     r'        this\.deletedAt = LocalDateTime\.now\(\);\s*\n'
     r'        this\.deletedBy = deletedBy;\s*\n'
     r'    \}',
     'public void softDelete(String deletedBy) {\n        this.setSupprime(true);\n        // BaseEntity gère deletedAt et deletedBy via les annotations\n    }'),
    
    (r'public void restore\(\) \{\s*\n'
     r'        this\.deleted = false;\s*\n'
     r'        this\.deletedAt = null;\s*\n'
     r'        this\.deletedBy = null;\s*\n'
     r'    \}',
     'public void restore() {\n        this.setSupprime(false);\n    }'),
    
    (r'public boolean isDeleted\(\) \{\s*\n'
     r'        return deleted != null && deleted;\s*\n'
     r'    \}',
     'public boolean isDeleted() {\n        return this.getSupprime() != null && this.getSupprime();\n    }'),
]

def fix_file(filepath):
    """Corrige un fichier en supprimant les champs redondants."""
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        original_content = content
        
        # Vérifier que l'entité étend BaseEntity
        if 'extends BaseEntity' not in content:
            return False, "N'étend pas BaseEntity"
        
        # Supprimer les sections de champs redondants
        for pattern, replacement in PATTERNS_TO_REMOVE:
            content = re.sub(pattern, replacement, content, flags=re.MULTILINE)
        
        # Supprimer les imports inutiles
        for import_pattern in IMPORTS_TO_REMOVE:
            content = re.sub(import_pattern, '', content)
        
        # Remplacer les méthodes
        for pattern, replacement in METHOD_REPLACEMENTS:
            content = re.sub(pattern, replacement, content, flags=re.MULTILINE)
        
        # Nettoyer les lignes vides multiples
        content = re.sub(r'\n\s*\n\s*\n+', '\n\n', content)
        
        if content != original_content:
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
            return True, "Corrigé"
        else:
            return False, "Aucun changement nécessaire"
    
    except Exception as e:
        return False, f"Erreur: {str(e)}"

def main():
    """Point d'entrée principal."""
    if len(sys.argv) < 2:
        print("Usage: python3 fix_redundant_fields.py <file1> [file2] ...")
        sys.exit(1)
    
    files = sys.argv[1:]
    corrected = 0
    errors = 0
    
    for filepath in files:
        if not os.path.exists(filepath):
            print(f"❌ {filepath}: Fichier introuvable")
            errors += 1
            continue
        
        success, message = fix_file(filepath)
        if success:
            print(f"✅ {filepath}: {message}")
            corrected += 1
        else:
            print(f"⚠️  {filepath}: {message}")
    
    print(f"\n📊 Résultats: {corrected} fichiers corrigés, {errors} erreurs")
    return 0 if errors == 0 else 1

if __name__ == '__main__':
    sys.exit(main())
