package ru.ardyc.cloud.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.AuthClient;
import ru.ardyc.auth.User;
import ru.ardyc.cloud.entity.CloudFileEntity;
import ru.ardyc.cloud.entity.CloudFolderEntity;
import ru.ardyc.cloud.model.CloudFile;
import ru.ardyc.cloud.model.CloudFolder;
import ru.ardyc.cloud.repo.CloudFilesRepository;
import ru.ardyc.cloud.repo.CloudFolderRepository;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CloudService {

    @Autowired
    public CloudFolderRepository folderRepository;

    @Autowired
    public CloudFilesRepository filesRepository;

    @Inject
    @RestClient
    public AuthClient userClient;

    @Autowired
    UploadService uploadService;

    public Response uploadFileInFolder(MultipartFormDataInput multipartFormDataInput) {
        try {
            String fileName = multipartFormDataInput.getFormDataMap().get("file_name").get(0).getBodyAsString();
            String userToken = multipartFormDataInput.getFormDataMap().get("user_token").get(0).getBodyAsString();
            String folderUUID = multipartFormDataInput.getFormDataMap().get("folder_uuid").get(0).getBodyAsString();
            String uuid = UUID.randomUUID().toString();

            CloudFolderEntity cloudFolderEntity = folderRepository.getCloudFolderEntityByUuid(folderUUID);
            if (cloudFolderEntity == null)
                return new OutputErrorResponse("Папка не найдена");

            User user = User.fromJson(userClient.getInfo(userToken));

            if (user == null)
                return new OutputErrorResponse("Пользователь не найден");

            if (!user.getEducationGroup().equals(cloudFolderEntity.getGroupUuid()))
                return new OutputErrorResponse("Вы не состоите в этой в группе");

            if (cloudFolderEntity.getFiles().stream().anyMatch(cloudFileEntity -> cloudFileEntity.getName().equals(fileName)))
                return new OutputErrorResponse("Файл с таким именем уже загружен");

            CloudFileEntity cloudFileEntity = new CloudFileEntity(fileName, folderUUID, uuid, cloudFolderEntity);
            uploadService.uploadFile(multipartFormDataInput, uuid);

            filesRepository.save(cloudFileEntity);
            cloudFolderEntity.getFiles().add(cloudFileEntity);
            folderRepository.save(cloudFolderEntity);
            return MessageResponse.create(CloudFile.fromEntity(cloudFileEntity));
        } catch (Exception e) {
            e.printStackTrace();
            return MessageResponse.create("Ошибка при загрузке файла, необходимы параметры file, file_name, user_token, folder_uuid");
        }
    }

    public Response uploadFile(MultipartFormDataInput input) {
        try {
            String fileName = input.getFormDataMap().get("file_name").get(0).getBodyAsString();
            String uuid = UUID.randomUUID().toString();
            CloudFileEntity cloudFileEntity = new CloudFileEntity(fileName, "null", uuid);
            uploadService.uploadFile(input, uuid);
            filesRepository.save(cloudFileEntity);
            return MessageResponse.create(CloudFile.fromEntity(cloudFileEntity));
        } catch (Exception e) {
            return MessageResponse.create("Ошибка при загрузке файла, необходимы параметры file, file_name");
        }
    }

    public Response uploadGroupFile(MultipartFormDataInput input) {
        try {
            String fileName = input.getFormDataMap().get("file_name").get(0).getBodyAsString();
            String userToken = input.getFormDataMap().get("user_token").get(0).getBodyAsString();

            User user = User.fromJson(userClient.getInfo(userToken));
            if (user == null)
                return new OutputErrorResponse("Пользователь не найден");

            String uuid = UUID.randomUUID().toString();
            CloudFileEntity cloudFileEntity = new CloudFileEntity(fileName, user.getEducationGroup(), uuid);
            uploadService.uploadFile(input, uuid);
            filesRepository.save(cloudFileEntity);
            return MessageResponse.create(CloudFile.fromEntity(cloudFileEntity));
        } catch (Exception e) {
            return MessageResponse.create("Ошибка при загрузке файла, необходимы параметры file,user_token, file_name");
        }
    }


    public Response createFolder(String userToken, String folderName) {
        try {
            User user = User.fromJson(userClient.getInfo(userToken));
            if (user == null)
                return new OutputErrorResponse("Пользователь не найден");

            if (user.getEducationGroup() == null || user.getEducationGroup().equals("null"))
                return new OutputErrorResponse("Вы не состоите в группе");
            CloudFolderEntity folder = folderRepository.getCloudFolderEntityByGroupUuidAndName(user.getEducationGroup(), folderName);
            if (folder != null)
                return new OutputErrorResponse("Папка с таким именем уже существует");

            String uuid = UUID.randomUUID().toString();
            folder = new CloudFolderEntity(uuid, folderName, user.getEducationGroup());
            folderRepository.save(folder);
            return MessageResponse.create(CloudFolder.fromEntity(folder));
        } catch (Exception e) {
            return new OutputErrorResponse("Ошибка при создании файла, необходимы параметры folderName, userToken");
        }
    }


    public Response getGroupFolders(String userToken) {
        try {
            User user = User.fromJson(userClient.getInfo(userToken));
            if (user == null)
                return new OutputErrorResponse("Пользователь не найден");

            if (user.getEducationGroup() == null || user.getEducationGroup().equals("null"))
                return new OutputErrorResponse("Вы не состоите в группе");
            List<CloudFolderEntity> cloudFolderEntity = folderRepository.getCloudFolderEntitiesByGroupUuid(user.getEducationGroup());
            return MessageResponse.create(cloudFolderEntity.stream().map(CloudFolder::fromEntity).collect(Collectors.toList()));
        } catch (Exception e) {
            return new OutputErrorResponse("Ошибка при получении файлов, необходимы параметры userToken");
        }
    }

    public Response deleteFolder(String userToken, String folderUuid) {
        try {
            User user = User.fromJson(userClient.getInfo(userToken));
            if (user == null)
                return new OutputErrorResponse("Пользователь не найден");

            if (user.getEducationGroup() == null || user.getEducationGroup().equals("null"))
                return new OutputErrorResponse("Вы не состоите в группе");

            CloudFolderEntity cloudFolderEntity = folderRepository.getCloudFolderEntityByUuid(folderUuid);
            if (cloudFolderEntity == null)
                return new OutputErrorResponse("Папка не найдена");

            if (!user.getEducationGroup().equals(cloudFolderEntity.getGroupUuid()))
                return new OutputErrorResponse("Вы не состоите в этой в группе");

            folderRepository.delete(cloudFolderEntity);
            return MessageResponse.create("Папка успешно удалена");
        } catch (Exception e) {
            return new OutputErrorResponse("Ошибка при удалении файла, необходимы параметры userToken, folderUuid");
        }
    }

    public InputStream getFile(String userToken, String fileUuid) {
        try {
            User user = User.fromJson(userClient.getInfo(userToken));
            if (user == null)
                return null;

            CloudFileEntity cloudFileEntity = filesRepository.getCloudFileEntityByUuid(fileUuid);
            if (cloudFileEntity == null)
                return null;

            if (cloudFileEntity.getFolderId().equals("null") || cloudFileEntity.getFolderId().equals(user.getEducationGroup())) {
                return new FileInputStream(uploadService.UPLOAD_DIR + fileUuid);
            }
            CloudFolderEntity cloudFolderEntity = folderRepository.getCloudFolderEntityByUuid(cloudFileEntity.getFolderId());
            if (cloudFolderEntity == null)
                return null;
            if (!user.getEducationGroup().equals(cloudFolderEntity.getGroupUuid()))
                return null;
            return new FileInputStream(uploadService.UPLOAD_DIR + fileUuid);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public Response getFileInfo(String fileUuid) {
        CloudFileEntity cloudFileEntity = filesRepository.getCloudFileEntityByUuid(fileUuid);
        if (cloudFileEntity == null)
            return new OutputErrorResponse("Файл не найден");

        return MessageResponse.create(CloudFile.fromEntity(cloudFileEntity));
    }

    public Response deleteFile(String userToken, String fileUuid) {
        User user = User.fromJson(userClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");

        CloudFileEntity cloudFileEntity = filesRepository.getCloudFileEntityByUuid(fileUuid);
        if (cloudFileEntity == null)
            return new OutputErrorResponse("Файл не найден");

        if (!cloudFileEntity.getFolderId().equals("null")) {
            CloudFolderEntity cloudFolderEntity = folderRepository.getCloudFolderEntityByUuid(cloudFileEntity.getFolderId());
            if (cloudFolderEntity == null)
                return new OutputErrorResponse("Папка не найдена");
            if (!user.getEducationGroup().equals(cloudFolderEntity.getGroupUuid()))
                return new OutputErrorResponse("Вы не состоите в этой в группе");
        }

        filesRepository.delete(cloudFileEntity);
        return MessageResponse.create("Файл успешно удален");
    }

}
