package org.adorsys.encobject.service;

/**
 * Service in charge of creating and deleting container
 * 
 * @author fpo
 *
 */
public class ContainerPersistence {
	
	private ExtendedStoreConnection blobStoreConnection;
	
	public ContainerPersistence(ExtendedStoreConnection storeConnection) {
		this.blobStoreConnection = storeConnection;
	}

	/**
	 * Creates a container in the blob store.
     *
	 * @param container container name
	 * @throws ContainerExistsException if a container with the same name already exists in the blob store.
	 */
	public void creteContainer(String container) throws ContainerExistsException {
		blobStoreConnection.createContainer(container);
	}
	
	/**
	 * Checks if a container with this name exists.
     *
     * @param container container name
     * @return true if a container with this name exists
	 */
	public boolean containerExists(String container){
		return blobStoreConnection.containerExists(container);
	}
	
	/**
	 * Delete the container with the given name.
     *
     * @param container container name
	 * @throws UnknownContainerException when no container with given name exists
	 */
	public void deleteContainer(String container) throws UnknownContainerException {
		blobStoreConnection.deleteContainer(container);
	}
}
