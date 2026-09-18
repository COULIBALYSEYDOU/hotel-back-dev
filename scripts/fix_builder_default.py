#!/usr/bin/env python3
"""
Script pour ajouter @Builder.Default sur tous les champs avec initialisation
dans les classes avec @Builder.
"""

import re
import os
from pathlib import Path

def add_builder_default_to_file(file_path):
    """Ajoute @Builder.Default avant les champs avec initialisation."""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Vérifier si la classe a @Builder
        if '@Builder' not in content:
            return False
        
        original_content = content
        modified = False
        
        # Pattern pour trouver les champs avec initialisation
        # Exemples:
        # private Boolean actif = true;
        # private Status status = Status.ACTIF;
        # private List<String> tags = new ArrayList<>();
        # private String nom = "";
        # private int count = 0;
        
        # Pattern plus précis pour éviter les faux positifs
        patterns = [
            # Boolean avec = true/false
            (r'(\s+)(private\s+(?:final\s+)?Boolean\s+\w+\s*=\s*(?:true|false);)', r'\1@Builder.Default\n\1\2'),
            # Integer/Long avec = 0 ou autre nombre
            (r'(\s+)(private\s+(?:final\s+)?(?:Integer|Long|int|long)\s+\w+\s*=\s*\d+;)', r'\1@Builder.Default\n\1\2'),
            # String avec = ""
            (r'(\s+)(private\s+(?:final\s+)?String\s+\w+\s*=\s*"";)', r'\1@Builder.Default\n\1\2'),
            # Enum avec = Enum.VALUE
            (r'(\s+)(private\s+(?:final\s+)?\w+\s+\w+\s*=\s*\w+\.\w+;)', r'\1@Builder.Default\n\1\2'),
            # Collections avec new
            (r'(\s+)(private\s+(?:final\s+)?(?:List|Set|Map|ArrayList|HashSet|HashMap)\s*<[^>]+>\s+\w+\s*=\s*new\s+\w+\(\);)', r'\1@Builder.Default\n\1\2'),
        ]
        
        for pattern, replacement in patterns:
            new_content = re.sub(pattern, replacement, content)
            if new_content != content:
                content = new_content
                modified = True
        
        # Vérifier qu'on n'a pas déjà @Builder.Default
        if modified:
            # Supprimer les doublons @Builder.Default
            content = re.sub(r'@Builder\.Default\s+@Builder\.Default', '@Builder.Default', content)
            
            # Ajouter l'import si nécessaire
            if '@Builder.Default' in content and 'import lombok.Builder;' in content:
                content = content.replace('import lombok.Builder;', 'import lombok.Builder;\nimport lombok.Builder.Default;')
            elif '@Builder.Default' in content and 'import lombok.*;' in content:
                # Déjà importé via lombok.*
                pass
            elif '@Builder.Default' in content:
                # Trouver où insérer l'import
                import_match = re.search(r'(import\s+lombok\.Builder;)', content)
                if import_match:
                    content = content[:import_match.end()] + '\nimport lombok.Builder.Default;' + content[import_match.end():]
        
        if modified and content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            return True
        
        return False
    except Exception as e:
        print(f"Erreur lors du traitement de {file_path}: {e}")
        return False

def main():
    """Parcourt tous les fichiers Java et ajoute @Builder.Default."""
    base_dir = Path('src/main/java')
    
    if not base_dir.exists():
        print(f"Erreur: {base_dir} n'existe pas")
        return
    
    java_files = list(base_dir.rglob('*.java'))
    modified_count = 0
    
    for java_file in java_files:
        if add_builder_default_to_file(java_file):
            print(f"✅ Modifié: {java_file}")
            modified_count += 1
    
    print(f"\n✅ {modified_count} fichiers modifiés")

if __name__ == '__main__':
    main()
