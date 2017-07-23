package org.shipkit.gradle.notes;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.shipkit.gradle.ShipkitConfiguration;
import org.shipkit.internal.gradle.notes.tasks.UpdateReleaseNotes;
import org.shipkit.internal.notes.model.ReleaseNotesData;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Generates incremental, detailed release notes text and appends them to the file {@link #getReleaseNotesFile()}.
 * When preview mode is enabled ({@link #isPreviewMode()}), the new release notes content is displayed only (file is not updated).
 */
public class UpdateReleaseNotesTask extends DefaultTask {

    //Due to the preview mode, we set the input/output of this property in the plugin implementation
    private File releaseNotesFile;
    private boolean previewMode;

    @Input @Optional private String previousVersion;
    @Input private String gitHubUrl;
    @Input private String gitHubRepository;
    @Input private Map<String, String> gitHubLabelMapping = new LinkedHashMap<String, String>();
    @Input private String publicationRepository;
    @InputFile private File releaseNotesData;
    @Input private Collection<String> developers = new LinkedList<String>();
    @Input private Collection<String> contributors = new LinkedList<String>();
    @InputFile private File contributorsDataFile;

    @Input private boolean emphasizeVersion;
    @Input private String version;
    @Input private String tagPrefix;

    /**
     * Generates incremental release notes and appends it to the top of release notes file.
     */
    @TaskAction
    public void updateReleaseNotes() {
        new UpdateReleaseNotes().updateReleaseNotes(this);
    }

    /**
     * @return true if task is configured to generate only preview of release notes (without appending them to the file), and false otherwise
     */
    public boolean isPreviewMode() {
        return previewMode;
    }

    /**
     * See {@link #isPreviewMode()} ()}
     */
    public void setPreviewMode(boolean previewMode) {
        this.previewMode = previewMode;
    }

    /**
     * Release notes file this task operates on.
     */
    public File getReleaseNotesFile() {
        return releaseNotesFile;
    }

    /**
     * See {@link #getReleaseNotesFile()}
     */
    public void setReleaseNotesFile(File releaseNotesFile) {
        this.releaseNotesFile = releaseNotesFile;
    }

    /**
     * The version we are generating the release notes for.
     */
    public String getVersion() {
        return version;
    }

    /**
     * See {@link #getVersion()}
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * See {@link ShipkitConfiguration.Git#getTagPrefix()}
     */
    public String getTagPrefix() {
        return tagPrefix;
    }

    /**
     * See {@link #getTagPrefix()}
     */
    public void setTagPrefix(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }

    /**
     * GitHub URL address, for example: https://github.com
     * See {@link ShipkitConfiguration.GitHub#getUrl()}
     */
    public String getGitHubUrl() {
        return gitHubUrl;
    }

    /**
     * See {@link #getGitHubUrl()}
     */
    public void setGitHubUrl(String gitHubUrl) {
        this.gitHubUrl = gitHubUrl;
    }

    /**
     * Name of the GitHub repository in format "user|org/repository",
     * for example: "mockito/mockito"
     */
    public String getGitHubRepository() {
        return gitHubRepository;
    }

    /**
     * See {@link #getGitHubRepository()}
     */
    public void setGitHubRepository(String gitHubRepository) {
        this.gitHubRepository = gitHubRepository;
    }

    /**
     * Issue tracker label mappings.
     * The mapping of "GitHub label" to human readable and presentable name.
     * The order of labels is important and will influence the order
     * in which groups of issues are generated in release notes.
     * Examples: ['java-9': 'Java 9 support', 'BDD': 'Behavior-Driven Development support']
     */
    public Map<String, String> getGitHubLabelMapping() {
        return gitHubLabelMapping;
    }

    /**
     * See {@link #getGitHubLabelMapping()}
     */
    public void setGitHubLabelMapping(Map<String, String> gitHubLabelMapping) {
        this.gitHubLabelMapping = gitHubLabelMapping;
    }

    /**
     * The target repository where the publications / binaries are published to.
     * Shown in the release notes.
     */
    public String getPublicationRepository() {
        return publicationRepository;
    }

    /**
     * See {@link #getPublicationRepository()}
     */
    public void setPublicationRepository(String publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    /**
     * Previous released version we generate the release notes from.
     */
    public String getPreviousVersion() {
        return previousVersion;
    }

    /**
     * See {@link #getPreviousVersion()}
     */
    public void setPreviousVersion(String previousVersion) {
        this.previousVersion = previousVersion;
    }

    /**
     * Input to the release notes generation,
     * serialized release notes data objects of type {@link ReleaseNotesData}.
     * They are used to generate formatted release notes.
     * The data file is generate by {@link FetchReleaseNotesTask}.
     */
    public File getReleaseNotesData() {
        return releaseNotesData;
    }

    /**
     * See {@link #getReleaseNotesData()}
     */
    public void setReleaseNotesData(File releaseNotesData) {
        this.releaseNotesData = releaseNotesData;
    }

    /**
     * Developers as configured in {@link ShipkitConfiguration.Team#getDevelopers()}
     */
    public Collection<String> getDevelopers() {
        return developers;
    }

    /**
     * See {@link #getDevelopers()}
     */
    public void setDevelopers(Collection<String> developers) {
        this.developers = developers;
    }

    /**
     * Contributors as configured in {@link ShipkitConfiguration.Team#getContributors()}
     */
    public Collection<String> getContributors() {
        return contributors;
    }

    /**
     * See {@link #getContributors()}
     */
    public void setContributors(Collection<String> contributors) {
        this.contributors = contributors;
    }

    /**
     * {@link #getContributorsDataFile()}
     */

    public void setContributorsDataFile(File contributorsDataFile) {
        this.contributorsDataFile = contributorsDataFile;
    }

    /**
     * File name from reads contributors from GitHub
     */
    public File getContributorsDataFile() {
        return contributorsDataFile;
    }

    /**
     * {@link #isEmphasizeVersion()}
     */
    public void setEmphasizeVersion(boolean emphasizeVersion) {
        this.emphasizeVersion = emphasizeVersion;
    }

    /**
     * Should current version be emphasized in release notes
     */
    public boolean isEmphasizeVersion() {
        return emphasizeVersion;
    }
}
