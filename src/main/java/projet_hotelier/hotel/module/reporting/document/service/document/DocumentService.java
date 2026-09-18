package projet_hotelier.hotel.module.reporting.document.service.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.document.dto.request.CreateDocumentRequest;
import projet_hotelier.hotel.module.reporting.document.dto.request.UpdateDocumentRequest;
import projet_hotelier.hotel.module.reporting.document.dto.response.DocumentResponse;
import projet_hotelier.hotel.module.reporting.document.mapper.DocumentMapper;
import projet_hotelier.hotel.module.reporting.document.model.DocumentModel;
import projet_hotelier.hotel.module.reporting.document.repository.DocumentRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository repository;
    private final DocumentMapper mapper;

    public DocumentResponse create(CreateDocumentRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation Document pour organisation={}", organisationId);
        DocumentModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodeDocument() != null && repository.existsByCodeDocument(entity.getCodeDocument())) {
            throw ConflictException.duplicate("Document", "codeDocument", entity.getCodeDocument());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public DocumentResponse update(String uuid, UpdateDocumentRequest request, Long organisationId, String username) {
        DocumentModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public DocumentResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        DocumentModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private DocumentModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        DocumentModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Document", "uuid", uuid);
        }
        return entity;
    }
}
