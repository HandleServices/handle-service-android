package br.com.handleservice.ui.preview;

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.handleservice.domain.model.Address;
import br.com.handleservice.ui.mock.getMockAddress

class AddressPreviewProvider : PreviewParameterProvider<Address> {
    override val values: Sequence<Address> = getMockAddress().asSequence()
}